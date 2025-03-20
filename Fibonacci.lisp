(defun factorial (n)
  (cond
    ((EQUAL n 0) ' 1)
    (t (* n (factorial (- n 1))))))

(factorial 5)