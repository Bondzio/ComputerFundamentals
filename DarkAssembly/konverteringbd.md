```
start 

    push 0      ;sum
    swap            ;swaps the order of the top two elements
    for:
        dup     ;copy the top item and puts it on top
                    ;jfalse - Jump if 0 is on the stack 
                    ;jtrue  - Jump if 1 is on the stack 
                    ;jmp        - Jump no matter
        jfalse slutt
        push 1  ;1 is placed on the stack
        sub

        rot     ;rot - the top three values are rotated so that the value that was on the top, is now located at the bottom
        push 2
        mul     ;Multiplication
        add
        swap
        jmp for
    slutt:
    drop
end 
```

```
;*************************************************************************** 
;* Løsningsforslag til Dark-oppgavene i AOC høsten 2003.                   *
;* Konvertering fra binær- til desimaltall på stakkmaskinarkitektur.       *
;* Per Kristian Lehre <lehre@idi.ntnu.no>                                  * 
;***************************************************************************
 main
    ; push 0    ; *
    ; push 1    ; *
    ; push 1    ; * Disse linjene legger inn bitstrengen
    ; push 0    ; * 11001100110 som argument til funksjonen.
    ; push 0    ; * Den desimale verdien er 1638.
    ; push 1    ; * 
    ; push 1    ; *
    ; push 0    ; *
    ; push 0    ; *
    ; push 1    ; *
    ; push 1    ; *
    ; push 11   ; *
    push 0
    swap
lokke
    dup
    jfalse binend
    push 1
    sub
    rot
    swap
    jfalse lend
    swap
    dup
    push 2
    swap
    call exp
    swap
    rot
    add
lend
    swap
    jmp lokke
binend
    drop
    stop
 
;*************************************
;* Eksponeringsrutine xy            * 
;*************************************  
; Push x, y times on the stack with 0 on the bottom

exp
    dup
    jfalse expzend
    push 0
    rot
expbeg
    push 1
    sub
    dup
    jfalse exploop
    swap
    dup
    rot
    swap
    jmp expbeg  ; Multiply the contents
exploop
    drop
explp
    swap
    dup
    jfalse expend
    mul
    jmp explp
expend
    drop
    ret
expzend
    drop
    drop
    push 1
    ret  

;**********************************

end main 

```
