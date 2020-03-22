package scan;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    static void ReversePrintStack(Stack<Point> s)
    {
        if (s.isEmpty())
            return;
        Point p = s.peek();
        s.pop();
        ReversePrintStack(s);
        System.out.print("\n\nThe point in the convex hull is : "+p.x+"\t"+p.y+"\n");
        s.push(p);
    }

    public static int direction(Point a, Point b, Point c) {
        int d = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
        if (d > 0)
            return -1;
        else if (d < 0)
            return 1;
        return 0;
    }

    public static void main(String[] args) throws FileNotFoundException {
        // write your code here
        System.out.println("Enter the required file path here :");
        Scanner filescanner=new Scanner(System.in);
        String filePath=filescanner.nextLine();

        Scanner scanner = new Scanner(new File(filePath));
        scanner.useDelimiter(",|\\s+");
        ArrayList<polarPoint> polarList = new ArrayList<polarPoint>();
        ArrayList<Point> pointList = new ArrayList<>();
        boolean xval = true;
        int i = 0;
        pointCreator tempPoint = new pointCreator();
        Stack<Point> convexHull = new Stack<Point>();
        while (scanner.hasNextInt()) {

            tempPoint.x = scanner.nextInt();

            tempPoint.y = scanner.nextInt();

            pointList.add(tempPoint.createPoint());
            pointList.get(i++).printPoint();
        }

        Collections.sort(pointList, new PointHeightComparator());
        for (int j = 0; j < pointList.size(); j++) {
            pointList.get(j).printPoint();
        }

        Collections.sort(pointList, new PointHeightComparator());

        polarList.add(new polarPoint(pointList.get(0), 0));



        for (int j = 1; j < pointList.size(); j++) {

            polarList.add(Point.createpolarPoint(pointList.get(0), pointList.get(j)));
        }



        Collections.sort(polarList, new PointAngleComparator());



        convexHull.push(pointList.get(0));


        for (int k = 1; k < polarList.size(); k++) {
            if (polarList.get(k) != null && k<polarList.size()-1) {
                if (polarList.get(k).angle == polarList.get(k + 1).angle) {
                    if (polarList.get(0).p.dist(polarList.get(k).p) < polarList.get(0).p.dist(polarList.get(k + 1).p)) {
                        polarList.remove(k);
                    } else {
                        polarList.remove(k + 1);
                    }
                }
            }
        }

        List<Point> noncollinear=new ArrayList<Point>();
        noncollinear.add(polarList.get(0).p);

        for (int k = 1; k < polarList.size(); k++) {
            if(polarList.get(k)!=null)
            {
                noncollinear.add(polarList.get(k).p);
            }
        }

        convexHull.push(noncollinear.get(1));
        if(noncollinear.size()>3) {
            convexHull.push(noncollinear.get(2));
        }



        if(noncollinear.size()>3) {

            for (int n = 3; n < noncollinear.size(); n++) {
                Point top = convexHull.peek();
                convexHull.pop();
                while (direction(convexHull.peek(), top, noncollinear.get(n)) != -1) {
                    top = convexHull.peek();
                    convexHull.pop();
                }
                convexHull.push(top);
                convexHull.push(noncollinear.get(n));
            }
        }

        ReversePrintStack(convexHull);

    }
}

class PointHeightComparator implements Comparator<Point> {
    public int compare(Point p1, Point p2) {
        return p1.getHeight() - p2.getHeight();
    }
}

class PointAngleComparator implements Comparator<polarPoint> {
    public int compare(polarPoint p1, polarPoint p2) {
        double delta = p1.getAngle() - p2.getAngle();
        if(delta > 0.00001) return 1;
        if(delta < -0.00001) return -1;
        return 0;
    }
}

class pointCreator{

    int x;
    int y;
Point createPoint(){
   Point tempPoint=new Point(x,y);
    return tempPoint;
}

}

class polarPoint{
    Point p;
    double angle;
    public polarPoint(Point p1,float angle1){
        p=p1;
        angle=angle1;
    }

   Point getPoint(){
        return p;
    }

    double getAngle(){
        return angle;
    }


}


class polarPointCreator{
    Point p;
    double angle;
    public static polarPoint createPolar(Point p1, float angle1){
        polarPoint tpoint=new polarPoint(p1,angle1);
        return tpoint;
    }
}

