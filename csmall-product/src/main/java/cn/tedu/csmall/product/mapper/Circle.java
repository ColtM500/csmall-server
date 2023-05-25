package cn.tedu.csmall.product.mapper;

import static java.lang.Math.abs;

public class Circle {
    int x;
    int y;
    int radius;

    public Circle(int x, int y, int r){
        this.x = x;
        this.y = y;
        this.radius = r;
    }



    public void CompareDistance(int x1, int y1, int r1, int x2, int y2, int r2) {
        double d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        if (d > (r1 + r2) * (r1 + r2)) {
            System.out.printf("无关");
        }
        else if (d < (r1 - r2) * (r1 - r2)) {
            System.out.println("包含");
        }
        else {
            System.out.printf("相交");
        }
    }

    public boolean RectangleCompare(int ax1, int ax2, int ay1, int ay2,
                                 int bx1, int bx2, int by1, int by2){
        int DistanceX = abs(ax1 + ax2 -bx1 - bx2);
        int TwoRanctangleDistanceX  = abs(ax1 - ax2) + abs(bx1 - bx2);
        int DistanceY = abs(ay1 + ay2 - by1 - by2);
        int TwoRanctangleDistanceY  = abs(ay1 - ay2) + abs(by1 - by2);
        if(DistanceX <= TwoRanctangleDistanceX && DistanceY <= TwoRanctangleDistanceY)
            return true;
        else
            return false;
    }
}
