package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.OfferService;
import domain.Offer;

@Controller
@RequestMapping("/offer")
public class OfferController extends AbstractController {

    // Services -----------------------------------------------------------

    @Autowired
    private OfferService offerService;

    // Constructors -----------------------------------------------------------

    public OfferController() {
        super();
    }

    // List ------------------------------------------------------------------		

    //Muestra un listado de ofertas no finalizadas
    @RequestMapping(value = "/list-not-finish", method = RequestMethod.GET)
    public ModelAndView listNotFinish() {

        ModelAndView result;
        Collection<Offer> offers;

        offers = offerService.findNotFinished();

        result = new ModelAndView("offer/list");
        result.addObject("offers", offers);
        result.addObject("requestURI", "offer/list-not-finish.do");

        return result;
    }

    //Muestra los detalles de una oferta dado su id
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public ModelAndView details(@RequestParam int offerId) {

        ModelAndView result;
        Offer offer = offerService.findOneToEdit(offerId);

        result = createEditModelAndView(offer);

        result.addObject("offer", offer);
        result.addObject("details", true);
        result.addObject("createOffer", false);
        result.addObject("detailsOffer", true);

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

        result = new ModelAndView("offer/edit");
        result.addObject("offer", offer);
        result.addObject("message", message);
        result.addObject("createOffer", false);
        result.addObject("detailsOffer", true);

        return result;
    }
}