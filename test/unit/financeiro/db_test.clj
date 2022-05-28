(ns financeiro.db-test
  (:require [clojure.test :refer :all]
            [financeiro.db :refer :all]))

(deftest db-transacoes
  (testing "Testa funções do banco de dados"
    (is (limpar))
    (is (= (count @registros) 0))
    (is (= (registrar {:valor 7 :tipo "receita"}) {:id 1 :valor 7 :tipo "receita"}))
    (is (= (saldo) 7))
    (is (= (registrar {:valor 6 :tipo "despesa"}) {:id 2 :valor 6 :tipo "despesa"}))
    (is (= (saldo) 1))
    (is (= (registrar {:valor 6 :tipo "despesa"}) {:id 3 :valor 6 :tipo "despesa"}))
    (is (= (saldo) -5))
    (is (limpar))
    (is (= (count @registros) 0))))

