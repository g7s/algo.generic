;; Generic interfaces for mathematical functions

;; by Konrad Hinsen

;; Copyright (c) Konrad Hinsen, 2009-2011. All rights reserved.  The use
;; and distribution terms for this software are covered by the Eclipse
;; Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;; which can be found in the file epl-v10.html at the root of this
;; distribution.  By using this software in any fashion, you are
;; agreeing to be bound by the terms of this license.  You must not
;; remove this notice, or any other, from this software.

;; Cort Spellman, 2015:
;; Changes of abs and round to be manually defined for various types instead
;; of via defmathfn-1.

(ns
    ^{:author "Konrad Hinsen"
      :doc "Generic math function interface
           This library defines generic versions of common mathematical
           functions such as sqrt or sin as multimethods that can be
           defined for any type."}
    clojure.algo.generic.math-functions
    (:require [clojure.algo.generic.arithmetic :as ga]
              [clojure.algo.generic.comparison :as gc])
    (:require-macros [clojure.algo.generic.macros :refer [defmathfn-1 defmathfn-2]]))


; List of math functions taken from
; https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Math
; Happens to be one place where javascript actually is related to Java, so mostly
; identical with the list in clojure.algo.generic taken from
; http://java.sun.com/j2se/1.4.2/docs/api/java/lang/Math.html
(defmathfn-1 abs)
(defmathfn-1 acos)
(defmathfn-1 asin)
(defmathfn-1 atan)
(defmathfn-2 atan2)
(defmathfn-1 ceil)
(defmathfn-1 cos)
(defmathfn-1 exp)
(defmathfn-1 floor)
(defmathfn-1 log)
(defmathfn-2 pow)
(defmathfn-1 round)
(defmathfn-1 sin)
(defmathfn-1 sqrt)
(defmathfn-1 tan)

;
; Sign
;
(defmulti sgn
  "Return the sign of x (-1, 0, or 1)."
  {:arglists '([x])}
  type)

(defmethod sgn :default
  [x]
  (cond (gc/zero? x) 0
        (gc/> x 0) 1
        :else -1))

;
; Conjugation
;
(defmulti conjugate
  "Return the conjugate of x."
  {:arglists '([x])}
  type)

(defmethod conjugate :default
  [x] x)

;
; Square
;
(defmulti sqr
  "Return the square of x."
  {:arglists '([x])}
  type)

(defmethod sqr :default
  [x]
  (ga/* x x))

;
; Approximate equality for use with floating point types
;
(defn approx=
  "Return true if the absolute value of the difference between x and y
   is less than eps."
  [x y eps]
  (gc/< (abs (ga/- x y)) eps))
