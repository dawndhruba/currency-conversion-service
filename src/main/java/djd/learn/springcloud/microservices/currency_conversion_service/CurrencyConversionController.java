package djd.learn.springcloud.microservices.currency_conversion_service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private Environment environment;
	
	@GetMapping(path = "/currency-conversion/from/{fromCurrency}/to/{toCurrency}/quantity/{quantity}")
	public ResponseEntity<CurrencyConversion> convertCurrency(@PathVariable String fromCurrency,@PathVariable String toCurrency,@PathVariable BigDecimal quantity) {
		CurrencyConversion conversion = new CurrencyConversion();
		conversion.setFrom(fromCurrency);
		conversion.setTo(toCurrency);
		conversion.setQuantity(quantity);
		conversion.setEnvironment(environment.getProperty("local.server.port"));
		
		ResponseEntity<CurrencyExchange> rsp = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{fromCurrency}/to/{toCurrency}", CurrencyExchange.class, fromCurrency, toCurrency);
		conversion.setConversionMultiple(rsp.getBody().getConversionMultiple());
		conversion.setTotalCalculatedAmount(quantity.multiply(rsp.getBody().getConversionMultiple()));
		
		return new ResponseEntity<CurrencyConversion>(conversion, HttpStatus.OK);
	}
}
