package com.xiaweizi.transitiondemo.bean;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.xiaweizi.transitiondemo.bean.ColorBean
 *     e-mail : 1012126908@qq.com
 *     time   : 2020/03/24
 *     desc   :
 * </pre>
 */
public class ColorBean {
    private int color;
    private String content;
    private String desc;

    public ColorBean() {
    }

    public ColorBean(int color, String content, String desc) {
        this.color = color;
        this.content = content;
        this.desc = desc;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "ColorBean{" +
                "color=" + color +
                ", content='" + content + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
