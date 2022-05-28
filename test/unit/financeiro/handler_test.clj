(ns financeiro.handler-test
  (:require [clojure.test :refer :all]
            [cheshire.core :as json]
            [ring.mock.request :as mock]
            [financeiro.handler :refer :all]
            [financeiro.db :as db]))


(deftest app-routs

  (testing "Rout: /"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) "Hello World"))))

  (testing "Limpa todas as eventuais transações do banco de dados"
    (let [response (app (mock/json-body (mock/request :post "/limpar") {}))]
      (is (= (:status response) 201))
      (is (= (:body response)"{\"saldo\":0}"))))

  (testing "Rout: /saldo"
    (let [response (app (mock/request :get "/saldo"))]
      (is (= (:status response) 201))
      (is (= (get-in response [:headers "Content-Type"]) "application/json; charset=utf-8"))
      (is (= (:body response) "{\"saldo\":0}"))))

  (testing "Rout: /nao-encontrada 404"
    (let [response (app (mock/request :get "/nao-encontrada"))]
      (is (= (:status response) 404)))))

(deftest app-transacoes
  (testing "Limpa todas as eventuais transações do banco de dados"
    (let [response (app (mock/json-body (mock/request :post "/limpar") {}))]
      (is (= (:status response) 201))
      (is (= (:body response)"{\"saldo\":0}"))))
  
  (testing "adiciona uma transação no valor de 10"
    (let [response (app (mock/json-body (mock/request :post "/transacoes") {:valor 10 :tipo "receita"}))]
     (is (= (:status response) 201))
     (is (= (:body response) "{\"id\":1,\"valor\":10,\"tipo\":\"receita\"}"))))
  
  (testing "verifica se o saldo atual é 10"
    (let [response (app (mock/request :get "/saldo"))]
      (is (= (:status response) 201))
      (is (= (:body response) "{\"saldo\":10}")))))
