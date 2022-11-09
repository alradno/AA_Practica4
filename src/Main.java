public class Main {

    public static void main(String[] args) {

        int[] ds = {10, 1, 7, 7};
        int[] cs = {8, 4, 2, 1};
        int[] ds2 = {10, 4, 3, 6, 9, 4, 4};
        int[] cs2 = {64, 32, 16, 8, 4, 2, 1}; //8+4+3+8+4+4 = 31 //solOptima: 10 + 4 + 6 + 9 + 4 + 4 = 37 (reinicio en 3)
        int[] ds3 = {5,4,6};
        int[] cs3 = {9,6,0};

        /*int resultado5 = procesar3(ds, cs);
        int resultado6 = procesar3(ds2, cs2);
        int resultado7 = procesar4(ds, cs);
        int resultado8 = procesar4(ds2, cs2);
        int resultado9 = procesar5(ds, cs);
        int resultado10 = procesar5(ds2, cs2);*/

        int resultado11 = procesar4(ds3, cs3);
        //int resultado12 = procesar5(ds3, cs3);


        /*System.out.println("Procesar3: "+resultado5);
        System.out.println("Procesar4: " +resultado7);
        System.out.println("Procesar5: "+resultado9);
        System.out.println();
        System.out.println("Procesar3: " +resultado6);
        System.out.println("Procesar4: " +resultado8);
        System.out.println("Procesar5: " +resultado10);
        System.out.println();*/
        System.out.println("Procesar4: " +resultado11);
        //System.out.println("Procesar5: " +resultado12);

    }

    public static int procesar4 (int[] ds, int[] cs){
        int [] combinaciones = new int[ds.length];
        int cota = 0;
        //Cota inicial = suma de todos los elementos de ds(maximo posible irreal)
        for(int i = 0; i < ds.length; i++){
            cota += ds[i];
        }
        //Llama a metodo auxiliar
        return aux4(ds, cs, 0, combinaciones, 0, cota, false);
    }

    public static int[] comparar(int[] ds, int [] cs, int [] combinaciones){

        int [] resultado = {0,0};
        int indice = 0;
        //Se comprueba si la combinacion del subArbol actual es mejor que la solucion optima hallada hasta ahora
        for(int j=0; j<cs.length; j++){
            if(combinaciones[j] == 1 ){
                resultado[0] += Math.min(ds[j], cs[indice]);
                indice++;
            }else if(combinaciones[j] == 0){
                indice = 0;
            }
        }
        resultado[1] = indice;
        return resultado;
    }

    public static int aux4(int[] ds, int[] cs, int etapa, int [] combinaciones, int solSubArbol, int cota, boolean podar){
        //Arbol binario, en cada etapa se puede reiniciar=0 o seguir=1 el array de cs
        for(int i=0; i<=1; i++){

            //Array que guarda de forma temporal la solucion del subarbol y se actualiza en cada iteracion
            combinaciones[etapa] = i;

            if(etapa == ds.length-2){
                //resultado[0] = solucion del subarbol, resultado[1] = indice de cs
                int [] resultado = comparar(ds, cs, combinaciones);

                //Si la sol de la penultima etapa es <= a la (solActual + beneficioEstimado) -> COTA, la rama se poda
                cota = resultado[0]+Math.min(ds[etapa+1], cs[resultado[1]]);
                podar = false;
                //Se poda si la mejor solucion posible por esa rama(cota) es menor a solSubArbol
                if(cota <= solSubArbol){
                   podar = true;
                }

            }
            //Si no se poda significa que la cota es mayor a la solucion optima hallada hasta ahora
            //i==1 para podar todas las ramas de reinicio a los nodos hoja(nunca va a ser mejor reiniciar en la ultima etapa)
            if((etapa == ds.length-1 && i == 1) && (!podar)){
                solSubArbol = cota;
            }
            //Continua el backtracking si no se ha podado la rama actual y no ha llegado al final de ds
            else if (etapa < ds.length-1 && !podar){
                //Guarda en solSubArbol la solucion de la hoja
                solSubArbol = aux4(ds, cs, etapa+1, combinaciones, solSubArbol, cota, false);
            }
        }
        //Marca que la combinación ha sido evaluada
        combinaciones[etapa] = -1;

        //Devuelve el resultado del subarbol
        return solSubArbol;
    }

    public static int procesar3 (int[] ds, int[] cs){
        int [] combinaciones = new int[ds.length];
        //Llama a mettodo auxiliar
        return aux3(ds, cs, 0, combinaciones, 0);
    }


    public static int aux3(int[] ds, int[] cs, int etapa, int [] combinaciones, int solSubArbol){

        for(int i=0; i<=1; i++){
            //Array que guarda de forma temporal la solucion del subarbol y se actualiza en cada iteracion
            combinaciones[etapa] = i;
            //Si es una hoja
            if(etapa == ds.length-1){
                int resultado = 0;
                int indice = 0;
                //Se comprueba si la combinacion del subArbol actual es mejor que la solucion optima hallada hasta ahora
                for(int j=0; j<cs.length; j++){
                    if(combinaciones[j] == 1 ){
                        resultado += Math.min(ds[j], cs[indice]);
                        indice++;
                    }else{
                        indice = 0;
                    }
                }
                if(resultado > solSubArbol){
                    solSubArbol = resultado;
                }

            }else{
                //Si no es una hoja, recorre de forma recursiva el subarbol hasta la profundidad de ds.length-1
                solSubArbol = aux3(ds, cs, etapa+1, combinaciones, solSubArbol);
            }
        }
        //Marca que la combinación ha sido evaluada
        combinaciones[etapa] = -1;
        //Devuelve el resultado del subarbol
        return solSubArbol;
    }

    public static int procesar (int[] ds, int[] cs){

        int resultado = 0;
        int indice = 0;

        for(int i = 0; i < ds.length; i++){
            //Si no reinicia
            if(cs[indice] > ds[i]){
                resultado += ds[i];
                indice++;
            }
            //Si reinicia
            else{
                indice = 0;
            }
        }
        return resultado;
    }

    public static int procesar2 (int[] ds, int[] cs){

        int resultado = 0;
        int indice = 0;
        int mediads = 0;
        int mediacs = 0;

        //Media del array ds
        for(int i = 0; i < ds.length; i++){
            mediads += ds[i];
        }
        mediads = mediads / ds.length;

        //Media cs
        for(int i = 0; i < cs.length; i++){
            mediacs += cs[i];
        }
        mediacs = mediacs / cs.length;

        for(int i = 0; i < ds.length; i++){
            //No reinicia
            if(cs[indice] > ds[i]){
                resultado += ds[i];
                indice++;
                //Si es el ultimo dia no se reinicia(coge lo que puede)
            } else if (i == ds.length - 1) {
                resultado += Math.min(cs[indice], ds[i]);
            }
            else{
                //Si es un día que merece la pena no reiniciar
                if(ds[i] > mediads && cs[indice] > mediacs){
                    resultado += Math.min(ds[i], cs[indice]);
                    indice++;
                }
                //Reinicia Servidor (la recompensa es muy baja si no se hace)
                else{
                    indice = 0;
                }
            }
        }
        return resultado;
    }

    /*public static int procesar5 (int[] ds, int[] cs){
        int [] combinaciones = new int[ds.length];
        int cota = 0;
        for(int i = 0; i < ds.length; i++){
            cota += cs[i];
        }
        int beneficioMaximo = 0;
        for(int i=0; i < ds.length; i++){
            if(ds[i] > beneficioMaximo){
                beneficioMaximo = ds[i];
            }
        }
        if(cs[0] > beneficioMaximo){
            beneficioMaximo = cs[0];
        }
        //Llama a metodo auxiliar
        return aux3(ds, cs, 0, combinaciones, 0);
    }

    public static int aux3(int[] ds, int[] cs, int etapa, int [] combinaciones, int solSubArbol){
        //Arbol binario, en cada etapa se puede reiniciar=0 o seguir=1
        for(int i=0; i<=1; i++){
            //Array que guarda de forma temporal la solucion del subarbol y se actualiza en cada iteracion

            combinaciones[etapa] = i;

            if(etapa == ds.length-1 && i == 1){
                int resultado = 0;
                int indice = 0;
                //Se comprueba si la combinacion del subArbol actual es mejor que la solucion optima hallada hasta ahora
                for(int j=0; j<cs.length; j++){
                    if(combinaciones[j] == 1 ){
                        resultado += Math.min(ds[j], cs[indice]);
                        indice++;
                    }else{
                        indice = 0;
                    }
                }
                if(resultado > solSubArbol){
                    solSubArbol = resultado;
                }
            }
            else if (etapa < ds.length-1 ){
                //Si no es una hoja, recorre de forma recursiva el subarbol hasta la profundidad de ds.length-1
                solSubArbol = aux3(ds, cs, etapa+1, combinaciones, solSubArbol);
            }
        }
        //Marca que la combinación ha sido evaluada
        combinaciones[etapa] = -1;
        //Devuelve el resultado del subarbol
        return solSubArbol;
    }*/
}