(defun factorial (n)
  (if (<= n 1)
      1
      (* n (factorial (- n 1)))))
      
;; Ejemplo de uso
(print (factorial 5))  ;; Debería imprimir 120
