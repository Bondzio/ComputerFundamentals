```
main
    ;Skriv inn din kode her.
;heltallet legges i register 5

 
load $2, 2                      ;load 2 til register 2
whilestart:
    jge $2, $5, primtall    ;if $2 < $5: primtall
    mod $3, $5, $2              ; else $5 mod $2 legge til $3
; register 0 ($0) is always 0
; register 1 ($1) is reserved for assembler 
; register 31 ($31) is used for the stack
    jeq $3, $0, whileslutt  ;if $3 == 0 finish
; inc - increment a register by 1 
;load $5, 13 dec - decrement a register by 1    
    inc $2  
    jmp whilestart              ;jump to whilestart
    
    primtall:
        load $6, 1              ;legge 1 til $6
whileslutt:
end main>
```
