public class Main {

    public static void main(String[] args) {
        double[][] A = {
                {3.2, 1.0, 1.0},
                {1.0, 3.7, 1.0},
                {1.0, 1.0, 4.2}
        };
        double[] B = {4, 4.5, 4};

        double X[] = solve(A, B);

        System.out.println("Matrix X:");
        for(int i = 0; i < X.length; i++){
            System.out.printf("X%d = %.2f \n", i,  X[i]);
        }
    }

    public static double[][] createMatrixT(double[][] A) {
        int n = A.length;
        double[][] T = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double sum = 0;
                if (i == j) {
                    for (int k = 0; k < i; k++) {
                        sum += T[k][i] * T[k][i];
                    }
                    T[i][j] = Math.sqrt(A[i][i] - sum);
                }
                else if(i < j){
                    for (int k = 0; k < i; k++) {
                        sum += T[k][i] * T[k][j];
                    }
                    T[i][j] = (A[i][j] - sum)/T[i][i];
                }
                else if(i > j){
                    T[i][j] = 0;
                }
            }
        }
        return T;
    }

    public static double[] findY(double[][] T, double[] B) {
        int n = T.length;
        double[] Y = new double[n];

        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < i; j++) {
                sum += T[j][i] * Y[j];
            }
            Y[i] = (B[i] - sum) / T[i][i];
        }

        return Y;
    }

    public static double[] findX(double[][] U, double[] Y) {
        int n = U.length;
        double[] X = new double[n];

        for (int i = n - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < n; j++) {
                sum += U[i][j] * X[j];
            }
            X[i] = (Y[i] - sum) / U[i][i];
        }

        return X;
    }

    public static double[] solve(double[][] A, double[] B){

        for(int i = 0; i < A.length; i++){
            int sum = 0;
            for(int j = 0; j < A.length; j++){
                if(i!=j){
                    sum+=Math.abs(A[i][j]);
                    //System.out.println(i + ": " + sum);
                }
            }
            if(Math.abs(A[i][i]) <= sum){
                System.out.println("Метод квадратных корней не применим");
                return null;
            }
        }

        var T = createMatrixT(A);

        for(int i = 0; i < T.length; i++){
            if(T[i][i] == 0){
                System.out.println("Система не имеет обределённого единственного решения");
                return null;
            }
        }

//        System.out.println("Upper Triangular Matrix:");
//        printMatrix(T);
//        System.out.println("Lower Triangular Matrix:");
//        printMatrix(transpose(T));

        double[] Y = findY(T, B);

//        System.out.println("Matrix Y:");
//        for(int i = 0; i < Y.length; i++){
//            System.out.printf("Y%d = %.2f \n", i,  Y[i]);
//        }
        double[] X = findX(T, Y);

        return X;
    }

    public static double[][] transpose(double[][] A) {
        int n = A.length;
        double[][] AT = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                AT[i][j] = A[j][i];
            }
        }
        return AT;
    }

    public static void printMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.printf("%.2f. ", matrix[i][j]);
            }
            System.out.println();
        }
    }
}