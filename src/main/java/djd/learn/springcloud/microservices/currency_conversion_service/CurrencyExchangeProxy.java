package djd.learn.springcloud.microservices.currency_conversion_service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "currency-exchange", url = "localhost:8000")
public interface CurrencyExchangeProxy {
	@GetMapping(path = "/currency-exchange/from/{fromCurrency}/to/{toCurrency}")
	public ResponseEntity<CurrencyExchange> getExchangeRate(@PathVariable String fromCurrency, @PathVariable String toCurrency);
}
