package scan;



public class Point {
    int x;
    int y;

public Point(int newX, int newY)
{
x=newX;
y=newY;
}

public Point() {

}


public int dist(Point p2)

{
    return (x - p2.x) * (x - p2.x) + (y - p2.y) * (y - p2.y);
}



public  int orientation(Point p, Point q, Point r)
{
        int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        if (val == 0)
            return 0;
        return (val > 0) ? 1 : 2;
}

    public void printPoint(){

    }

    public int getHeight(){
    return y;
    }


    public static polarPoint createpolarPoint(Point p1,Point p2){
        double tempAngle=0;
        polarPoint tmpPolar=new polarPoint(null,0);
        tempAngle=Math.atan2(p2.y-p1.y,p2.x-p1.x);
        tmpPolar.p=p2;
        tmpPolar.angle=tempAngle;
        return tmpPolar;
    }
}


