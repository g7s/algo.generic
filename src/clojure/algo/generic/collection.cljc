;; Generic interfaces for collection-related functions

;; by Konrad Hinsen

;; Copyright (c) Konrad Hinsen, 2009-2011. All rights reserved.  The use
;; and distribution terms for this software are covered by the Eclipse
;; Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;; which can be found in the file epl-v10.html at the root of this
;; distribution.  By using this software in any fashion, you are
;; agreeing to be bound by the terms of this license.  You must not
;; remove this notice, or any other, from this software.

(ns
    ^{:author "Konrad Hinsen"
      :doc "Generic collection interface
           This library defines generic versions of common
           collection-related functions as multimethods that can be
           defined for any type."}
    clojure.algo.generic.collection
    (:refer-clojure :exclude [assoc conj dissoc empty get into seq]))

;
; assoc
;
(defmulti assoc
  "Returns a new collection in which the values corresponding to the
   given keys are updated by the given values. Each type of collection
   can have specific restrictions on the possible keys."
   {:arglists '([coll & key-val-pairs])}
   (fn [coll & items] (type coll)))

(defmethod assoc :default
  [map & key-val-pairs]
  (apply #?(:clj clojure.core/assoc :cljs cljs.core/assoc) map key-val-pairs))

;
; conj
;
(defmulti conj
  "Returns a new collection resulting from adding all xs to coll."
   {:arglists '([coll & xs])}
  (fn [coll & xs] (type coll)))

(defmethod conj :default
  [coll & xs]
  (apply #?(:clj clojure.core/conj :cljs cljs.core/conj) coll xs))

;
; dissoc
;
(defmulti dissoc
  "Returns a new collection in which the entries corresponding to the
   given keys are removed. Each type of collection can have specific
   restrictions on the possible keys."
   {:arglists '([coll & keys])}
   (fn [coll & keys] (type coll)))

(defmethod dissoc :default
  [map & keys]
  (apply #?(:clj clojure.core/dissoc :cljs cljs.core/dissoc) map keys))

;
; empty
;
(defmulti empty
  "Returns an empty collection of the same kind as the argument"
   {:arglists '([coll])}
   type)

(defmethod empty :default
  [coll]
  (#?(:clj clojure.core/empty :cljs cljs.core/empty) coll))

;
; get
;
(defmulti get
  "Returns the element of coll referred to by key. Each type of collection
   can have specific restrictions on the possible keys."
   {:arglists '([coll key] [coll key not-found])}
  (fn [coll & args] (type coll)))

(defmethod get :default
  ([coll key]
   (#?(:clj clojure.core/get :cljs cljs.core/get) coll key))
  ([coll key not-found]
   (#?(:clj clojure.core/get :cljs cljs.core/get) coll key not-found)))

;
; into
;
(defmulti into
  "Returns a new coll consisting of to-coll with all of the items of
  from-coll conjoined. A default implementation based on reduce, conj, and
  seq is provided."
   {:arglists '([to from])}
  (fn [to from] (type to)))

(declare seq)
(defmethod into :default
  [to from]
  (reduce conj to (seq from)))

;
; seq
;
(defmulti seq
  "Returns a seq on the object s."
  {:arglists '([s])}
  type)

(defmethod seq :default
  [s]
  (#?(:clj clojure.core/seq :cljs cljs.core/seq) s))
