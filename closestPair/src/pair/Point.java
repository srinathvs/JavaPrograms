package pair;

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

     //   System.out.println("\n"+x+"\t"+y+"\tIs a point\n");
    }

    public int getHeight(){
        return y;
    }

    public int getX(){
        return x;
    }

}
