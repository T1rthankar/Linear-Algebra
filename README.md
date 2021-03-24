# Linear-Algebra

This repo contains the code I wrote to take a matrix or a system of linear equations as input and return its ref or rref as output. The code uses a homegrown `Fraction` class (which uses `BigInteger`s under the hood to store the numerator and the denominator) to store arbitrary-precision rational numbers, and does its calculations on objects of the `Matrix` class, which is simply a 2D array filled with `Fraction`s.
