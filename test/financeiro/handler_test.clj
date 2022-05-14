(ns financeiro.handler-test
  (:require [clojure.test :refer :all]
            [midje.sweet :refer :all]
            [ring.mock.request :as mock]
            [financeiro.handler :refer :all]))


(facts "testando a raíz do projeto"
       (let [response (app (mock/request :get "/"))]
         (fact "Resposta da aplicação" (:status response) => 200)
         (fact "Texto do corpo é Hello World" (:body response) => "Hello World")))

(facts "Página não encontrada"
       (let [response (app (mock/request :get "/undefined"))]
         (fact "Erro 404" (:status response) => 404)))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) "Hello World"))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
