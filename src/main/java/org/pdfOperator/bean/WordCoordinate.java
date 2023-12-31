package org.pdfOperator.bean;

// 关键词坐标
public class WordCoordinate {

    private float x;
    private float y;
    private float height;
    private float width;

    private int page;

    public WordCoordinate(float x, float y, float height, float width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public WordCoordinate(float x, float y, float height, float width, int page) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.page = page;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "WordCoordinate{" +
                "x=" + x +
                ", y=" + y +
                ", height=" + height +
                ", width=" + width +
                ", page=" + page +
                '}';
    }
}
