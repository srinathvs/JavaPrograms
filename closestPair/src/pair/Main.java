package pair;
import java.io.File;
import java.lang.reflect.Array;
import java.util.*;
public class Main {

    static float INFINITE=100000000;

    public static int direction(Point a, Point b, Point c) {
        int d = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
        if (d > 0)
            return -1;
        else if (d < 0)
            return 1;
        return 0;
    }

    static float min(float x, float y)
    {
        return (x < y)? x : y;
    }

    public static float dist(Point p1, Point p2)

    {
        return (float) Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
    }

    static float bruteForce(ArrayList<Point> P,Pair pr)
    {
        float min=INFINITE;
        int n=P.size()-1;
        for (int i = 0; i <= n; ++i) {
            for (int j = i + 1; j <= n; ++j) {
                if (dist(P.get(i), P.get(j)) < min) {
                    min = dist(P.get(i), P.get(j));
                    pr.p1=P.get(i);
                    pr.p2=P.get(j);
                    pr.delta=min;
                   // pr.PrintPair();
                }
            }

        }
      //  System.out.println("\nthe closest distance is\n"+min+"\n");
        return min;

    }

    static float closestPair(Point[] Px) throws Exception {
    Pair pair=new Pair();
    int n = (Px.length);
    if(n>0) {
        if (n <= 3) {
            ArrayList<Point> p = new ArrayList<>();
            Collections.addAll(p, Px);
            return bruteForce(p,pair);
        }
        int mid = n / 2;
        Point midPoint = Px[mid];
        int left = n / 2;
        int right = n / 2 + n % 2;
        Point[] lPoints = new Point[left];
        Point[] rPoints = new Point[right];
        float Leftmin;
        float Rightmin;


       // System.out.println("\nLeftSubarray\n");
        for (int i = 0; i < left; i++) {
            lPoints[i] = Px[i];
            lPoints[i].printPoint();
        }
        //System.out.println("\nRightSubarray\n");
        for (int i = 0; i < right; i++) {
            rPoints[i] = Px[left + i];
            rPoints[i].printPoint();
        }

        Leftmin = closestPair(lPoints);
        Rightmin = closestPair(rPoints);

        float delta = min(Leftmin, Rightmin);
       // System.out.println(delta+"\nIs delta\n");

        Point[] area = new Point[n];
        int z = 0;
        for (int i = 0; i < n; i++) {
            if (Math.abs(Px[i].x - midPoint.x) < delta) {
                area[z] = Px[i];
                z++;
            }
        }
        float d1=closestinArea(area,delta,pair);
        //System.out.println("The closest distance is :"+min(delta,d1));
        return min(delta, d1);
    }
    else{
        return 0;
    }
    }



    public static float closestinArea(Point[] area,float delta,Pair pr){
        float min = delta;
        ArrayList<Point> strip=new ArrayList<>();
        for(int m=0;m<area.length;m++){
            if(area[m]!=null){
                strip.add(area[m]);
            }
        }
       // strip.forEach((p)->p.printPoint());
        Collections.sort(strip,new PointYComparator());
        for (int i = 0; i < strip.size()-1; ++i) {
            for (int j = i + 1; j < strip.size()-1 && (strip.get(j).y - strip.get(i).y) < min; ++j) {
                if (dist(strip.get(i), strip.get(j)) < min) {
                    min = dist(strip.get(i), strip.get(j));
                    pr.p1=strip.get(i);
                    pr.p2=strip.get(j);
                    pr.delta=min;
                }
            }
        }

        return min;


    }
    public static void main(String[] args) throws Exception {
	// write your code here
        int i = 0;

        System.out.println("Enter the required file path here :");
        Scanner filescanner=new Scanner(System.in);
        String filePath=filescanner.nextLine();

        Scanner scanner = new Scanner(new File(filePath));
        scanner.useDelimiter(",|\\s+");


        ArrayList<Point> pointListX = new ArrayList<>();
        ArrayList<Point> pointListY = new ArrayList<>();

        pointCreator tempPoint = new pointCreator();
        while (scanner.hasNextInt()) {

            tempPoint.x = scanner.nextInt();

            tempPoint.y = scanner.nextInt();

            pointListX.add(tempPoint.createPoint());
            pointListY.add(tempPoint.createPoint());

        }
        Collections.sort(pointListX,new PointXComparator());
        Point[] PX=new Point[pointListX.size()];
        PX=pointListX.toArray(PX);
        for(Point p:PX){
          //  p.printPoint();
        }
        float finaldist;
        ArrayList<Pair> pairList=new ArrayList<>();
        finaldist=closestPair(PX);
        for(int k=0;k<pointListX.size();k++) {
            for (int l = k+1; l < pointListX.size(); l++) {
                if(!(pairList.contains(new Pair(pointListX.get(k), pointListX.get(l)))) || !(pairList.contains(new Pair(pointListX.get(l),pointListX.get(k))))) {
                    pairList.add(new Pair(pointListX.get(k), pointListX.get(l)));
                }
            }
        }
       pairList.forEach(p->p.compareDelta(finaldist));

      //  pairList.forEach(p->p.PrintPair());

      //  pointListX.forEach((p)->p.printPoint());
/*
        ArrayList<Point> P1=new ArrayList<>();
        Collections.addAll(P1,PX);
        bruteForce(P1);
*/


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


class PointXComparator implements Comparator<Point> {
    public int compare(Point p1, Point p2) {
        return p1.getX() - p2.getX();
    }
}

class PointYComparator implements Comparator<Point> {
    public int compare(Point p1, Point p2){
        return p1.getHeight()-p2.getHeight();
    }
}

class Pair{
    Point p1;
    Point p2;
    float delta;
    Pair(Point px,Point py){
        p1=px;p2=py;
        delta= (float) Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
    }

    public Pair() {

    }

    void PrintPair(){
        System.out.println("The distance is : "+delta+" The points are : \t"+p1.x+","+p1.y+"-------------"+p2.x+","+p2.y+"\n");
    }

    void compareDelta(float dist){
        if(delta==dist){
            PrintPair();
        }
    }
}


