package com.example.dyw.myapplication.DB;

import org.litepal.crud.DataSupport;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by dyw on 2017/7/14.
 */

public abstract class DBSupport <T extends DBSupport> extends DataSupport {
    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    /**
     * 根据你自己定义的主键到数据库中查找这个实体<p></p>
     * 需要将主键作为实体类的第一个字段，就可以通过反射获得主键名和主键值
     *
     * @return 泛型实体类对象
     */
    @SuppressWarnings("unchecked")
    protected T find() {
        try {
            Class clazz = getGenericType();
            Field pk = clazz.getDeclaredFields()[0];
            pk.setAccessible(true);
            String pkValue = pk.get(this) + "";
            if (pkValue.isEmpty()) {
                return null;
            }
            List<T> list = where(pk.getName() + " = ?", pkValue).find(clazz);
            if (list == null || list.size() == 0) {
                return null;
            } else if (list.size() > 1) {
                for (int i = 1; i < list.size(); i++) {
                    list.get(i).delete();
                }
            }
            return list.get(0);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 在该数据库框架中，
     * Id只允许long或int类型，
     * 所以自定义的String类型的personId不唯一，
     * 会导致存储多个相同的数据。
     * 需要手动查重。
     *
     * @return 是否插入成功
     */
    @Override
    public synchronized boolean save() {
        T t = find();//根据主键名和主键值获取数据库中实例
        if (t == null || t.id == 0) {//不存在
            return super.save();
        } else {
            this.id = t.id;//更新字段
            return super.save();//update
        }
    }

    /**
     * 获得泛型类型
     * <p></p>
     * 注：其中DBSupport<T>是泛型类
     * 在父类中声明getGenericType
     * 子类继承具体的DBSupport<Person>
     * 那么在子类中就可以通过getGenericType()获取到Person的class.
     *
     * @return T.class
     */
    private Class getGenericType() {
        Type genType = getClass().getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (!(params[0] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[0];
    }
}
