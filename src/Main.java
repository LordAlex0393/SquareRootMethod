public class Main {

    public static void main(String[] args) {
//        double[][] originalMatrix = {
//                {4.0, 1.0, 2.0},
//                {1.0, 9.0, 3.0},
//                {2.0, 3.0, 16.0}
//        };

        double[][] originalMatrix = {
                {3.2, 1.0, 1.0},
                {1.0, 3.7, 1.0},
                {1.0, 1.0, 4.2}
        };
        double[] B = {4, 4.5, 4};

        var T = createMatrixT(originalMatrix);

        System.out.println("Upper Triangular Matrix:");
        printMatrix(T);
        System.out.println("Lower Triangular Matrix:");
        printMatrix(transpose(T));

        // Решаем систему уравнений
        double[] Y = findY(T, B);
        System.out.println("Matrix Y:");
        System.out.printf("%.2f. %.2f. %.2f.\n", Y[0], Y[1], Y[2]);
        double[] X = findX(T, Y);

        // Выводим решение
        System.out.println("Solving a system of equations:");
        for (int i = 0; i < X.length; i++) {
            System.out.println("X[" + i + "] = " + X[i]);
        }
    }

    public static void printMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.printf("%.2f. ", matrix[i][j]);
            }
            System.out.println();
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
}