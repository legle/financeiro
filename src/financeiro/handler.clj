(ns financeiro.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [cheshire.core :as json]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.json :refer [wrap-json-body]]
            [financeiro.db :as db]))

(defn como-json
  [conteudo & [status]]
  {:status (or status 200)
     :headers {"Content-Type" "application/json; charset=utf-8"}
     :body (json/generate-string conteudo)})

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/despesas" [] (como-json {:transacoes (db/transacoes-do-tipo "despesa")}))
  (GET "/receitas" [] (como-json {:transacoes (db/transacoes-do-tipo "receita")}))
  (GET "/saldo" [] (como-json {:saldo (db/saldo)} 201))
  (GET "/transacoes" {filtros :params} (como-json {:transacoes (if (empty? filtros) (db/transacoes) (db/transacoes-com-filtro filtros))}))
  (POST "/transacoes" requisicao (como-json (db/registrar (:body requisicao)) 201))
  (POST "/limpar" requisicao (como-json {:saldo (db/limpar)} 201))
  (route/not-found "Not Found"))

(def app
  (wrap-json-body (wrap-defaults app-routes api-defaults) {:keywords? true :bigdecimals? true}))
