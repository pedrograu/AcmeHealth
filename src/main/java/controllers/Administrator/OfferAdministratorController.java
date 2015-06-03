package controllers.Administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.OfferService;
import services.SpecialistService;
import controllers.AbstractController;
import domain.Administrator;
import domain.Offer;
import domain.Specialist;

@Controller
@RequestMapping("/offer/administrator")
public class OfferAdministratorController extends AbstractController {

	// Services -----------------------------------------------------------

	@Autowired
	private OfferService offerService;

	@Autowired
	private SpecialistService specialistService;
	
	@Autowired
	private AdministratorService administratorService;
	

	// Constructors -----------------------------------------------------------

	public OfferAdministratorController() {
		super();
	}

	// List ------------------------------------------------------------------

	@RequestMapping(value = "/list-own", method = RequestMethod.GET)
	public ModelAndView listOwn() {

		ModelAndView result;
		Collection<Offer> offers;

		offers = offerService.findOwn();

		result = new ModelAndView("offer/list");
		result.addObject("offers", offers);
		result.addObject("own", true);
		result.addObject("requestURI", "offer/administrator/list-own.do");

		return result;
	}

	@RequestMapping(value = "/list-not-finished", method = RequestMethod.GET)
	public ModelAndView listNotFinished() {

		ModelAndView result;
		Collection<Offer> offers;

		offers = offerService.findNotFinishedOwn();

		result = new ModelAndView("offer/list");
		result.addObject("offers", offers);

		result.addObject("requestURI",
				"offer/administrator/list-not-finished.do");

		return result;
	}

	@RequestMapping(value = "/list-active", method = RequestMethod.GET)
	public ModelAndView listActive() {

		ModelAndView result;
		Collection<Offer> offers;

		offers = offerService.findActive();

		result = new ModelAndView("offer/list");
		result.addObject("offers", offers);

		result.addObject("requestURI", "offer/administrator/list-active.do");

		return result;
	}

	@RequestMapping(value = "/list-order", method = RequestMethod.GET)
	public ModelAndView listOrder() {

		ModelAndView result;
		Collection<Offer> offers;

		offers = offerService.findOrder();

		result = new ModelAndView("offer/list");
		result.addObject("offers", offers);

		result.addObject("requestURI", "offer/administrator/list-order.do");

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;

		Offer offer;

		offer = offerService.create();

		result = createEditModelAndView(offer);

		result.addObject("offer", offer);
		result.addObject("createOffer", true);
		result.addObject("detailsOffer", false);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int offerId) {

		ModelAndView result;

		Offer offer = offerService.findOneToEdit(offerId);

		result = createEditModelAndView(offer);
		result.addObject("createOffer", false);
		result.addObject("detailsOffer", true);

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int offerId) {
	
			ModelAndView result;
			Offer offer = offerService.findOneToEdit(offerId);
			Collection<Offer> offers;
			
			offers = offerService.findOwn();

			
			boolean canDelete = offerService.canDelete(offer);
			
			if(canDelete){
				offerService.delete(offer);
				result = new ModelAndView("redirect:list-own.do");
				
			}
			

			else{
				result = new ModelAndView("offer/list");
				result.addObject("mostrarError",true);
				result.addObject("own", true);
				result.addObject("offers",offers);
				result.addObject("requestURI", "offer/administrator/list-own.do");
					
			}

	
			return result;
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ModelAndView detail(@RequestParam int offerId) {

		ModelAndView result;
		Offer offer = offerService.findOneToEdit(offerId);

		result = new ModelAndView("offer/edit");

		result.addObject("offer", offer);
		result.addObject("details", true);
		result.addObject("createOffer", false);
		result.addObject("detailsOffer", true);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Offer offer, BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(offer);
		} else {
			try {
				offerService.save(offer);
				result = new ModelAndView("redirect:list-own.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(offer, "offer.commit.error");
			}
		}

		return result;

	}


	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(Offer offer) {
		assert offer != null;
		ModelAndView result;

		result = createEditModelAndView(offer, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Offer offer, String message) {

		Assert.notNull(offer);
		ModelAndView result;
		Collection<Specialist> specialists;

		Administrator administratorConnect = administratorService.findByPrincipal();
		result = new ModelAndView("offer/edit");
		specialists = specialistService.findAllSpecialists();

		result.addObject("offer", offer);
		result.addObject("specialists", specialists);
		result.addObject("requestURI", "offer/administrator/edit.do?offerId="
				+ offer.getId());
		result.addObject("message", message);
		result.addObject("createOffer", true);
		result.addObject("detailsOffer", false);

		return result;
	}

}
