import sys

from random import randint
from math import ceil


def multiply(inp1, inp2):
    """Karatsuba multiplication of integer numbers"""

    inp1_len = len(str(inp1))
    inp2_len = len(str(inp2))

    m = max(inp1_len, inp2_len)

    if m == 1:
        return inp1 * inp2

    m_h = int(ceil(float(m)/2))

    inp1_str = str(inp1).zfill(m)
    inp2_str = str(inp2).zfill(m)

    a, b = int(inp1_str[:m-m_h]), int(inp1_str[-m_h:])
    c, d = int(inp2_str[:m-m_h]), int(inp2_str[-m_h:])

    a_c = multiply(a, c)
    b_d = multiply(b, d)
    gauss_term = multiply(a+b, c+d) - a_c - b_d

    return 10**(2*m_h)*a_c + 10**m_h*gauss_term + b_d


if __name__ == '__main__':
    """Test Karatsuba multiplication
    This script accepts either 1 or 2 integer parameters which determine the
    size of the random numbers generated to test the algorithm. The default is 10
    """

    args = len(sys.argv)

    if args == 2:
        n = m = int(sys.argv[1])
    elif args == 3:
        n = int(sys.argv[1])
        m = int(sys.argv[2])
    else:
        n = m = 10

    input1 = randint(10 ** (n - 1), 10 ** n - 1)
    input2 = randint(10 ** (m - 1), 10 ** m - 1)

    print("a =", input1)
    print("b =", input2)
    out = input1 * input2
    print("m =", out)

    recursive_m = multiply(input1, input2)

    if out == recursive_m:
        print("correct")
    else:
        print("wrong")
        print("r =",recursive_m)
