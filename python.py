# -*- coding: utf-8 -*-

'''Age of Computers 3 '''

def ds(s, r):            # Disk system
    rps = r / 60.0
    rs = 1 / rps
    return rs * 1000000 / s

# Programmer I/O-beregning
def io(bakgrounnKrever, instruksjoner):
    ut = 1000000
    return ut * (1 - bakgrounnKrever) / instruksjoner

def dma(bakgrounnKrever):
    ut = 1000000
    print
    ut * 5 * (1 - bakgrounnKrever) + bakgrounnKrever * 3 * ut

# DMA, oppgave 3
def dma(bakgrounnKrever, maskinsykler, bussen):
    ut = 1000000
    print
    ut * maskinsykler * (1 - bakgrounnKrever) + bakgrounnKrever * (maskinsykler - bussen) * ut

# DMA oppgave 2
def dma2(bit_per_sek, bit_per_bokstav, mips):
    print(bit_per_sek / bit_per_bokstav) / ( mips * (10.0 ** 6) )

# DMA oppgave 1
def dma1(sektorerPerSpor, diskRotererRPM, avbruddMikrosekund):
    lesetidPerSektor = (60.0 / diskRotererRPM) / sektorerPerSpor
    print((avbruddMikrosekund / (10.0 ** 6)) / lesetidPerSektor)

# Hurtigbufferberegning
def hurtigbuffer(L1p, L2p, L1aksesstid, snitt, Minneaksesstid):
    print(snitt - (L1p * L1aksesstid + (1 - L1p) * (1 - L2p) * Minneaksesstid)) / ((1 - L1p) * L2p)

# Operasjonstid
def operasjonstid(oppdateringssykel, oppdateringsoperasjon):
    print(oppdateringsoperasjon / ((1.0 / oppdateringssykel) * (10.0 ** 6)))

# RAID: nedre grense for tidsbruk for å lese fil fra disk
def lesedisk(omdreiningerPrSek, sektorerPrSpor, sektorstorrelseB, soketidMs, filstorrelseM):
    print(( (filstorrelseM * 1024) / (omdreiningerPrSek * sektorerPrSpor * sektorstorrelseB)) + soketidMs / 1000)

# Lesetid return sekund
def lesetid(omdreiningerPrMinutt, sektorerPrSpor, sektorstorrelseB, soketidMs, filstorrelseM):
    print(((filstorrelseM * (1024 ** 2)) / (
        (omdreiningerPrMinutt / 60.0) * sektorerPrSpor * sektorstorrelseB)) + soketidMs / 1000)

# Lagerstørrelse: svar kB
def lagerstorrelse(buffer1KB, buffer2MB, primerMB, eksternminneGB):
    print(buffer1KB + buffer2MB * 1024 + primerMB * 1024 + eksternminneGB * (1024 ** 2))

# Diskaksess return ns nanosecond
def diskaksess(hurtigbufferNS, hovedlagerNS, diskMS, hurtigbufferTreffrate, hovedlageretTreffrate):
    search1 = hurtigbufferNS * hurtigbufferTreffrate
    search2 = (hurtigbufferNS + hovedlagerNS) * (1 - hurtigbufferTreffrate) * hovedlageretTreffrate
    search3 = (hurtigbufferNS + hovedlagerNS + (diskMS * (10.0 ** 6))) * (1 - hurtigbufferTreffrate) * (
        1 - hovedlageretTreffrate)
    print(search1 + search2 + search3)

# Mikroprosessoren svare KB
def mikroprosessoren(bitAdresser, bitDatabuss):
    print((2 ** bitAdresser) * bitDatabuss / (1024 * 8))

# Prosessor, beregning
def prosessorBeregning(antallInstruksjonerPerKlokkesyklus, aksessLager, hurtigbufferTraffrate, aksesstid,
                       hurtigbufferlinjeHenteFraHovedlagerByte, hurtigbufferHovedlagerBussenMHz, bussBredBit):
    busshastighet = hurtigbufferHovedlagerBussenMHz * bussBredBit
    prosessorKravet = (antallInstruksjonerPerKlokkesyklus / aksessLager) * (
        1 - hurtigbufferTraffrate) * (hurtigbufferlinjeHenteFraHovedlagerByte * 8)
    print(( busshastighet / prosessorKravet))
    print(hurtigbufferHovedlagerBussenMHz / (antallInstruksjonerPerKlokkesyklus * (1 - hurtigbufferTraffrate)))