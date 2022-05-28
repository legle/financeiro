(ns financeiro.handler-accept-test
  (:require [midje.sweet :refer :all]
            [cheshire.core :as json]
            [clj-http.client :as http]
            [financeiro.auxiliar-accept-test :refer :all]))

(against-background
 [(before :facts (iniciar-servidor porta-padrao))
  (after :facts (parar-servidor))]

 (fact "O saldo inicial é 0" :aceitacao
       (json/parse-string (conteudo "/saldo") true) => {:saldo 0})

 #_(fact "O saldo é igual a 10 depois de uma transação de 10" :aceitacao
       (http/post (endereco-para "/transacoes") {:content-type :json :body (json/generate-string {:valor 10 :tipo "receita"})})
       (json/parse-string (conteudo "/saldo") true) => {:saldo 10}))
