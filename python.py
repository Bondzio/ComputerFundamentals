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

# Hurtigbufferberegning
def hurtigbuffer(L1p, L2p, L1aksesstid, snitt, Minneaksesstid):
    print(snitt - (L1p * L1aksesstid + (1 - L1p) * (1 - L2p) * Minneaksesstid)) / ((1 - L1p) * L2p)

# Operasjonstid
def operasjonstid(oppdateringssykel, oppdateringsoperasjon):
    print(oppdateringsoperasjon / ((1.0 / oppdateringssykel) * (10.0 ** 6)))

