package be.dnsbelgium.rdap;

import be.dnsbelgium.rdap.core.Domain;
import be.dnsbelgium.rdap.core.Entity;
import be.dnsbelgium.rdap.core.RDAPError;
import be.dnsbelgium.rdap.service.EntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "entity")
public class EntityController {

	private final static Logger logger = LoggerFactory.getLogger(EntityController.class);

	private final String baseRedirectURL;

	private final int redirectThreshold;

	@Autowired
	public EntityController(@Value("#{applicationProperties['baseRedirectURL']}") String baseRedirectURL,
			@Value("#{applicationProperties['redirectThreshold']}") int redirectThreshold) {
		this.baseRedirectURL = baseRedirectURL;
		this.redirectThreshold = redirectThreshold;
	}

	@Autowired
	private EntityService entityService;

	@RequestMapping(value = "/{handle}", method = RequestMethod.GET, produces = Controllers.CONTENT_TYPE)
	@ResponseBody
	public Entity get(@PathVariable("handle") final String handle) throws RDAPError {
		logger.debug("Query(GET) for entity with handle: {}", handle);
		Entity entity = entityService.getEntity(handle);
		if (entity == null) {
			logger.debug("Entity result for {} is null. Throwing EntityNotFound Error", handle);
			throw RDAPError.entityNotFound(handle);
		}
		return entity;
	}
	
	@RequestMapping(value = "/{handle}", method = RequestMethod.HEAD, produces = Controllers.CONTENT_TYPE)
	public ResponseEntity<Void> head(@PathVariable("handle") final String handle) throws RDAPError {
		logger.debug("Query(HEAD) for entity with handle: {}", handle);
		Entity entity = entityService.getEntity(handle);
		if (entity == null) {
			logger.debug("Entity result for {} is null. Throwing EntityNotFound Error", handle);
			throw RDAPError.entityNotFound(handle);
		}
		return new ResponseEntity<Void>(null, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(value = "/{handle}", method = { RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.OPTIONS,
			RequestMethod.PATCH, RequestMethod.POST, RequestMethod.TRACE }, produces = Controllers.CONTENT_TYPE)
	@ResponseBody
	public Entity any(@PathVariable("handle") final String handle) throws RDAPError {
		throw RDAPError.methodNotAllowed();
	}

	public int getRedirectThreshold() {
		return redirectThreshold;
	}

	public String getBaseRedirectURL() {
		return baseRedirectURL;
	}

}
