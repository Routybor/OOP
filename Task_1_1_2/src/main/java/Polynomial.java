/**
 * Polynomial implementation.
 */
public class Polynomial {

    private final int[] coeffs;
    private final int degree;

    public Polynomial(int[] arr) {
        degree = arr.length;
        coeffs = arr;
    }
    /**
     * Plus function.
     */
    public Polynomial plus(Polynomial term) {
        /* нахождения макс степени среди данных полиномов */
        int resDegree = Math.max(this.degree, term.degree);
        /* массив коэффов нового полинома, размер равен макс степени */
        int[] resCoeffs = new int[resDegree];
        int i;

        /* добавляются коэффы полиномов по степени к результату */
        for (i = 0; i < resDegree; i++) {
            if (i < this.degree) {
                resCoeffs[i] += this.coeffs[i];
            }
            if (i < term.degree) {
                resCoeffs[i] += term.coeffs[i];
            }
        }

        return new Polynomial(resCoeffs);
    }

    /**
     * Minus function
     */
    public Polynomial minus(Polynomial term) {
        int[] subCoeffs = new int[term.degree]; /* создаётся полином-копия терма */
        int i;

        /* запись данных в полином-копию со сменой знаков
        коэффов(знак минус перед полиномом по сути) */
        for (i = 0; i < term.degree; i++) {
            subCoeffs[i] = -term.coeffs[i];
        }

        /* суммирование полинома-уменьшаемого с полиномом-копией(отрицательным полиномом-термом) */
        return this.plus(new Polynomial(subCoeffs));
    }

    /**
     * Times function.
     */
    public Polynomial times(Polynomial term) {
        /* усли ничего не сокращается, степень полинома-результата этому и равно */
        int[] resCoeffs = new int[this.degree + term.degree - 1];
        int i;
        int j;

        for (i = 0; i < term.degree; i++) { /* поэлементное перемножение */
            for (j = 0; j < this.degree; j++) {
                resCoeffs[i + j] += (term.coeffs[i] * this.coeffs[j]);
            }
        }

        return new Polynomial(resCoeffs);
    }

    /**
     * Evaluate function.
     */
    public int evaluate(int x) {
        int xPow = 1; /* X в степени, сначала степень 0 и X равен 1 */
        int result = 0;
        int i;

        /* получение результата, если степень 0, ты коэффициент на X не домножается*/
        for (i = 0; i < this.degree; i++) {
            result += (this.coeffs[i] * (i == 0 ? 1 : xPow));
            xPow *= x; /* Возведение X в след степень */
        }

        return result;
    }

    /**
     * Differentiate function.
     */
    public Polynomial differentiate(int diffOrder) {
        if (diffOrder < 0) { /* отрицательная степень производной вызывает ошибку */
            System.err.println("Error. Differentiate order must be positive.");
            return null;
        } else if (diffOrder == 0) { /* нулевая степень производной не меняет полином */
            return this;
        } else if (diffOrder >= this.degree) { /* степень производной > степени полинома = ноль */
            return new Polynomial(new int[]{0});
        } else {
            int newDegree = this.degree - diffOrder;
            int[] newCoeffs = new int[newDegree];
            int order = diffOrder; /* копия степени для изменения */
            int i;
            int j;

            /* копия коэффов дифференцируемого полинома со смещением на степень производной */
            for (i = 0; i < newDegree; i++) {
                newCoeffs[i] = this.coeffs[i + diffOrder];
            }
            while (order > 0) {
                /* собственно дифференцирование, просто домножается коэф на степень*/
                for (j = 0; j < newDegree; j++) {
                    newCoeffs[j] *= (order + j);
                }
                order--;
            }

            return new Polynomial(newCoeffs);
        }
    }

    /**
     * Equals implementation.
     */
    public boolean equals(Polynomial term) {
        int i;

        /* при несовпадении степеней, проверка членов не имеет смысла */
        if (this.degree != term.degree) {
            return false;
        }
        for (i = 0; i < this.degree; i++) { /* поэлементная проверка полиномов */
            if (this.coeffs[i] != term.coeffs[i]) {
                return false;
            }
        }

        return true;
    }
    /**
     * ToString implementation.
     */
    public String toString() {
        String result = "";
        int resDegree = this.degree; /* степень последующих X */
        int index = resDegree - 1; /* индекс, берётся конец так как это первый член полинома */
        int coeffAbs;

        while (index >= 0) { /* как только доходит до начала - завершение */
            if (this.coeffs[index] != 0) { /* коэффициент равный 0 не отображается */
                if (resDegree != this.degree) { /* если не первый элемент => перед ним стоит знак*/
                    result += this.coeffs[index] > 0 ? " + " : " - ";
                }
                resDegree--;
                /* знак элемента уже обработан, нужно только значение */
                coeffAbs = this.coeffs[index] > 0 ? this.coeffs[index] : this.coeffs[index] * (-1);
                result += String.format("%d", coeffAbs);
                if (resDegree > 1) { /* необходимо отобразить степень X */
                    result += "x^";
                    result += String.format("%s", resDegree);
                } else if (resDegree == 1) { /* необходимо отобразить X */
                    result += "x";
                } else { /* пропуск */
                    resDegree--;
                }
            }
            index--;
        }

        return result.isEmpty() ? "0" : result; /* если полином нулевой, 0 и возвращается */
    }
}