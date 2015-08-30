package controllers.Specialist;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.OfferService;

import controllers.AbstractController;
import domain.Offer;

@Controller
@RequestMapping("/offer/specialist")
public class OfferSpecialistController extends AbstractController {

    // Services -----------------------------------------------------------

    @Autowired
    private OfferService offerService;

    // Constructors -----------------------------------------------------------

    public OfferSpecialistController() {
        super();
    }

    // List ------------------------------------------------------------------		

    //Lista las ofertas no finalizadas del especialista logueado
    @RequestMapping(value = "/list-not-finished", method = RequestMethod.GET)
    public ModelAndView listNotFinished() {

        ModelAndView result;
        Collection<Offer> offers;

        offers = offerService.findNotFinishedOwnSpecialist();

        result = new ModelAndView("offer/list");
        result.addObject("offers", offers);
        result.addObject("notFinish", true);
        result.addObject("requestURI", "offer/specialist/list-not-finished.do");

        return result;
    }

    //Muestra los detalles de una oferta dado su id
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

}
