
public class Expression {

    String expression;
    String[] tokens;
    String[] result;
    boolean isLexical = true;
    boolean isSyntaxique = true;



    int checkTab[][]={
   //       +----------------------------+
   //       | lt | ch | . | - | # |autre|
   //       +---------------------------+
   /*S0*/   {   1,  2,  3,  1,  -1, -1},
   /*ID*/   {   1,  1, -1,  1,   1, -1},
   /*int*/  {  -1,  2,  4, -1,   2, -1},
   /*S1*/   {  -1,  4, -1, -1,  -1, -1},
   /*float*/{  -1,  4, -1, -1,   4, -1}
    };

    public Expression(String expression){
        this.expression = expression;
        setExpression();

        for (int i=0;i<tokens.length;i++)
            System.out.print(tokens[i]);
        System.out.println();
        for (int i=0;i<result.length;i++)
            System.out.print(result[i]);
        System.out.println();
        System.out.println("Analyse lexical:");

        for (int i=0;i<result.length;i++){
            if (result[i].equals("chain non acceptee")) {
                System.out.println(tokens[i] + " --> " + result[i]);
                isLexical = false;
            }
        }
        if (isLexical){
            System.out.println("Accepter!");
            System.out.println("Analyse Syntaxique:");
            S();
            if(isSyntaxique)
                System.out.println("Accepter!");
            else {
                System.out.println("Non Accepter");
            }
        }
    }


    private void setExpression(){
        String reg = "((?<=[;|\\+|\\*|\\-|/|=|(|)])|(?=[;|\\+|\\*|\\-|/|=|(|)]))";
        String str = expression;
        tokens = str.split(reg);
        for (int i=0 ; i<tokens.length;i++) {
            tokens[i] = tokens[i].trim();
        }
        for (int i=0 ; i<tokens.length;i++) {
            if(tokens[i].equals("")){
                String[] temp = new String[tokens.length-1];
                for (int k=0 ;k<i; k++){
                    temp[k] = tokens[k];
                }
                for (int k =i ; k<temp.length;k++){
                    temp[k] = tokens[k+1];
                }
                tokens = temp;
                i--;
            }
        }

        result= new String[tokens.length];
        for (int i=0 ; i<tokens.length;i++){
            int Ec =0,j=0,end =tokens[i].length();
            while (Ec!=-1 && j<end){
                Ec = checkTab[Ec][nbr_co(tokens[i].charAt(j),j,end)];
                j++;
            }
            switch (Ec){
                case 1:
                    result[i] = "ID";
                    break;
                case 2:
                    result[i] = "Int";
                    break;
                case 4:
                    result[i] = "Float";
                    break;
                default:
                    result[i]= checkOp(tokens[i]);
                    break;
            }
        }

    }

    private int nbr_co(char c,int i, int end){
        if((c>='a' && c<='z') || (c>='A' && c <= 'Z'))
            return 0;
        if(c >= '0' && c<= '9')
            return 1;
        if(c == '.')
            return 2;
        if(c == '_')
            return 3;
        if (i+1 == end)
            return 4;
        return 5;
    }

    private String checkOp(String s){
        if (s.equals("="))
            return "=";
        else if (s.equals("("))
            return "(";
        else if (s.equals("+"))
            return "+";
        else if (s.equals("-"))
            return "-";
        else if (s.equals("*"))
            return "*";
        else if (s.equals("/"))
            return "/";
        else if (s.equals(")"))
            return ")";
        else if (s.equals(";"))
            return ";";
        else
            return "chain non acceptee";
    }

//    Analyse Syntaxique

    int tc = 0;

    private void S(){
        if(tc<result.length && result[result.length-1].equals(";")) {
            if (result[tc].equals("ID")) {
                tc++;
                if (result[tc].equals("+")||(result[tc].equals("-"))|| (result[tc].equals("*")||(result[tc].equals("/"))))
                    tc++;
                if (result[tc].equals("=")) {
                    tc++;
                    E();
                    if (isSyntaxique == true) {
                        if (result[tc] != ";" || tc != result.length - 1) {
                            isSyntaxique = false;
                        }
                    }
                } else {
                    isSyntaxique = false;
                }
            } else {
                isSyntaxique = false;
            }
        }else {
            isSyntaxique = false;
        }
    }

    private void A(){

        if(tc<result.length) {
            if (result[tc].equals("ID") || result[tc].equals("Int") || result[tc].equals("Float"))
                tc++;
            else {
                isSyntaxique = false;
            }
        }else {
            isSyntaxique = false;
        }
    }

    private void Op(){
        if(tc<result.length) {
            if (result[tc].equals("+") || result[tc].equals("-") || result[tc].equals("*") || result[tc].equals("/"))
                tc++;
            else {
                isSyntaxique = false;
            }
        }else {
            isSyntaxique = false;
        }
    }

    private void E(){
        if(tc<result.length){
            if(result[tc].equals("(")){
                tc++;
                E();
                if (isSyntaxique == true){
                    if (result[tc].equals(")")){
                        tc++;
                        if (result[tc].equals("+") || result[tc].equals("-") || result[tc].equals("*") || result[tc].equals("/"))
                            Ep();
                    }else {
                        isSyntaxique = false;
                    }
                }
            }else {
                if (result[tc].equals("ID") || result[tc].equals("Int") || result[tc].equals("Float")){
                    A();
                    if(isSyntaxique == true){
                        if (result[tc].equals("+") || result[tc].equals("-") || result[tc].equals("*") || result[tc].equals("/"))
                            Ep();
                    }
                }else {
                    isSyntaxique = false;
                }
            }
        }else isSyntaxique = false;
    }

    private void Ep(){
        if(tc<result.length){
            Op();
            if (isSyntaxique==true){
                E();
            }
        }else {
            isSyntaxique = false;
        }

    }

    @Override
    public String toString() {
        String tk = new String();
        String rs= new String();
        String lx ;
        String sx ;
        String errorLexical = new String();

        if (isLexical) {
            lx = "Accepter!";
            if (isSyntaxique)
                sx = "Accepter!";
            else
                sx = "Non Accepter";
        }
        else {
            lx = "Non Accepter";
            sx = "Non Accepter";
        }


        for (int i=0;i<tokens.length;i++) {
            tk += tokens[i] + " ";
            rs += result[i]+ " ";
        }

        for (int i=0;i<result.length;i++){
            if (result[i].equals("chain non acceptee")) {
                errorLexical +="                    " + tokens[i] + " --> " + result[i]+"\n";
            }
        }
                return "Expression:\n" +
                "                 " + tk + "\n" +
                "                 " + rs +"\n"+
                "Analyse lexical: \n"+
                "                    " + lx +"\n"+ errorLexical +"\n"+
                "Analyse Syntaxique:\n"+
                "                    " + sx +"\n"
                ;
    }
}
