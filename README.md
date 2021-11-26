# paypay-webhook-test
For testing of receive the PayPay Webhook post in Play Framework 2.8.x.

# Feature
* IP white lists of [Webhook通知元サーバーのIPアドレス](https://paypay.ne.jp/developers-faq/webhook/webhookip/)
* Receive webhook post from [トランザクションイベント](https://www.paypay.ne.jp/opa/doc/jp/v1.0/webcashier#tag/%E3%83%88%E3%83%A9%E3%83%B3%E3%82%B6%E3%82%AF%E3%82%B7%E3%83%A7%E3%83%B3%E3%82%A4%E3%83%99%E3%83%B3%E3%83%88)
* Receive webhook post from [突合ファイル](https://www.paypay.ne.jp/opa/doc/jp/v1.0/webcashier#tag/%E7%AA%81%E5%90%88%E3%83%95%E3%82%A1%E3%82%A4%E3%83%AB)

# Note
Client IP is the last IP of the request header 'X-Forwarded-For' (hard coding). 

# License
[here](LICENSE)