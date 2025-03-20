; Documento para hacer pruebas en LISP

(print (COND 
  ((ATOM 'A) 'EsAtom) 
  ((LIST '(1 2 3)) 'EsLista) 
  ((> 5 3) 'Mayor) 
  ((EQUAL 4 4) 'Igual)))
