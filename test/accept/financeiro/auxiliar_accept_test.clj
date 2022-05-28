(ns financeiro.auxiliar-accept-test
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [midje.sweet :refer :all]
            [clj-http.client :as http]
            [cheshire.core :as json]
            [financeiro.handler :refer [app]]))

(def servidor (atom nil))

(def porta-padrao 3001)

(defn iniciar-servidor
  [porta]
  (swap! servidor (fn [_] (run-jetty app {:port porta :join? false}))))

(defn parar-servidor
  []
  (.stop @servidor))

(defn endereco-para
  [rota]
  (str "http://localhost:" porta-padrao rota))

(def requisicao-para (comp http/get endereco-para))

(defn conteudo
  [rota]
  (:body (requisicao-para rota)))

(defn conteudo-como-json
  [transacao]
  {:content-type :json
   :body (json/generate-string transacao)
   :throw-exceptions false})

(defn despesa
  [valor]
  (conteudo-como-json {:valor valor :tipo "despesa"}))

(defn receita
  [valor]
  (conteudo-como-json {:valor valor :tipo "receita"}))

