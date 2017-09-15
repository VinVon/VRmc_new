package com.medvision.vrmc.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by raytine on 2017/3/24.
 */

public class CityInfo implements Serializable{

    /**
     * code : 110000
     * name : 北京市
     * fullName : 北京市
     * array : [{"code":"110100","name":"北京市","fullName":"北京市"}]
     */

    private String code;
    private String name;
    private String fullName;
    private List<ArrayBean> array;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<ArrayBean> getArray() {
        return array;
    }

    public void setArray(List<ArrayBean> array) {
        this.array = array;
    }

    public static class ArrayBean implements Serializable{
        /**
         * code : 110100
         * name : 北京市
         * fullName : 北京市
         */

        private String code;
        private String name;
        private String fullName;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
    }
}
