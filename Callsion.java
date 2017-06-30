package coll;

public class Callsion {

	public static void main(String[] args) {

		Point point1 = new Point();
		double[] x1 = {1,3,3};
		double[] y1 = {1,1,3};
		point1.setXs(x1);
		point1.setYs(y1);
		Point point2 = new Point();
		double[] x2 = {1,2,2,1};
		double[] y2 = {2,2,3,3};
		point2.setXs(x2);
		point2.setYs(y2);
		System.out.println(callsionTest(point1,point2));
	}

	/**
	 * 判断多边形是否碰撞
	 * 
	 * @param point1
	 * @param point2
	 * @return
	 */
	private static boolean callsionTest(Point point1, Point point2) {
		boolean isCallsion = false;
		double[] xs1 = point1.getXs();
		double[] ys1 = point1.getYs();
		double[] xs2 = point2.getXs();
		double[] ys2 = point2.getYs();
		for (int i = 0; i < xs1.length; i++) {
			//线段斜率
			double x11 = xs1[i];
			double y11 = ys1[i];
			double x12 = xs1[i==xs1.length-1?0:i+1];
			double y12 = ys1[i==xs1.length-1?0:i+1];
			double k1 = (y12-y11)/(x12-x11);
			double[] distance1 = new double[2];
			if(x12==x11){//斜率不存在
				distance1[0] = y11;
				distance1[1] = y12;
			}else{
				distance1 = getMaxS(x11,y11,k1,xs1,ys1);
			}
			double s11 = distance1[0];
			double s12 = distance1[1];
			//[s11,s12]为y轴投影碰撞判断区域
			isCallsion = false;
			for (int k = 0; k< xs2.length; k++) {
				double x21 = xs2[k];
				double y21 = ys2[k];
				double[] distance2 = new double[2];
				if(x12==x11){//斜率不存在
					distance2[0] = y11;
					distance2[1] = y12;
				}else{
					distance2 = getMaxS(x21,y21,k1,xs2,ys2);
				}
				double s21 = distance2[0];
				double s22 = distance2[1];
				if(getMax(s11,s12)<getMin(s21,s22)||getMin(s11,s12)>getMax(s21,s22)){
				}else{
					isCallsion = true;
					break;
				}
			}
			if(!isCallsion){//未碰撞
				return false;
			}
		}
		return true;
	}

	private static double getMax(double s1,double s2){
		return s1>s2?s1:s2;
	}
	private static double getMin(double s1,double s2){
		return s1<s2?s1:s2;
	}
	
	//获取y轴投影最大值
	private static double[] getMaxS(double x, double y, double k, double[] xs,
			double[] ys) {
		double s1 = y-k*x;
		double s2 = 0;
		double max = 0;
		for (int j = 0; j < ys.length; j++) {
			s2 = ys[j] - k*xs[j];
			max = Math.abs(max)>=Math.abs(s1-s2)?max:s2;
		}
		double[] distance = {s1,max};
		return distance;
	}
}
class Point{
	private double[] xs;
	private double[] ys;
	public double[] getXs() {
		return xs;
	}
	public void setXs(double[] xs) {
		this.xs = xs;
	}
	public double[] getYs() {
		return ys;
	}
	public void setYs(double[] ys) {
		this.ys = ys;
	}

}
