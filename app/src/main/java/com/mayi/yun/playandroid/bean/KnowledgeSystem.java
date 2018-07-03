package com.mayi.yun.playandroid.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class KnowledgeSystem  {

    /**
     * children : [{"children":[],"courseId":13,"id":60,"name":"Android Studio相关",
     * "order":1000,"parentChapterId":150,"visible":1},
     * {"children":[],"courseId":13,"id":169,"name":"gradle","order":1001,"parentChapterId":150,"visible":1}
     * ,{"children":[],"courseId":13,"id":269,"name":"官方发布","order":1002,"parentChapterId":150,"visible":1}]
     * courseId : 13
     * id : 150
     * name : 开发环境
     * order : 1
     * parentChapterId : 0
     * visible : 1
     */

    private int courseId;
    private int id;
    private String name;
    private int order;
    private int parentChapterId;
    private int visible;
    private ArrayList<Children> children;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getParentChapterId() {
        return parentChapterId;
    }

    public void setParentChapterId(int parentChapterId) {
        this.parentChapterId = parentChapterId;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public ArrayList<Children> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Children> children) {
        this.children = children;
    }

    public static class Children  implements Parcelable{
        /**
         * children : []
         * courseId : 13
         * id : 60
         * name : Android Studio相关
         * order : 1000
         * parentChapterId : 150
         * visible : 1
         */

        private int courseId;
        private int id;
        private String name;
        private int order;
        private int parentChapterId;
        private int visible;
     //   private List<Children> children;

        protected Children(Parcel in) {
            courseId = in.readInt();
            id = in.readInt();
            name = in.readString();
            order = in.readInt();
            parentChapterId = in.readInt();
            visible = in.readInt();
           // children = in.createTypedArrayList(Children.CREATOR);
        }

        public static final Creator<Children> CREATOR = new Creator<Children>() {
            @Override
            public Children createFromParcel(Parcel in) {
                return new Children(in);
            }

            @Override
            public Children[] newArray(int size) {
                return new Children[size];
            }
        };

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getParentChapterId() {
            return parentChapterId;
        }

        public void setParentChapterId(int parentChapterId) {
            this.parentChapterId = parentChapterId;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

    /*    public List<Children> getChildren() {
            return children;
        }

        public void setChildren(List<Children> children) {
            this.children = children;
        }*/

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(courseId);
            parcel.writeInt(id);
            parcel.writeString(name);
            parcel.writeInt(order);
            parcel.writeInt(parentChapterId);
            parcel.writeInt(visible);
         //   parcel.writeTypedList(children);
        }
    }
}
