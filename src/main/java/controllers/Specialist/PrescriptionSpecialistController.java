package controllers.Specialist;

import java.awt.Color;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import services.AppointmentService;
import services.PatientService;
import services.PrescriptionService;
import controllers.AbstractController;
import domain.Appointment;
import domain.Patient;
import domain.Prescription;

@Controller
@RequestMapping("/prescription/specialist")
public class PrescriptionSpecialistController extends AbstractController {

    // Services ----------------------------------------------------------------

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentService appointmentService;

    // Constructor ----------------------------------------------------------------

    public PrescriptionSpecialistController() {
        super();
    }

    //List --------------------------------------------------------------------
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam int patientId) {

        ModelAndView result;
        Patient patientConnect = patientService.findOneToEdit(patientId);
        Collection<Prescription> prescriptions;

        prescriptions = prescriptionService.findForPatient(patientConnect);

        result = new ModelAndView("prescription/list");
        result.addObject("prescriptions", prescriptions);
        result.addObject("requestURI", "prescription/specialist/list.do");

        return result;
    }

    // Details.....................

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public ModelAndView details(@RequestParam int prescriptionId) {

        ModelAndView result;

        Prescription prescription = prescriptionService.findOneToEdit(prescriptionId);

        result = new ModelAndView("prescription/edit");
        result.addObject("isPatient", true);
        result.addObject("prescription", prescription);

        return result;
    }
    
    // Edition ----------------------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(@RequestParam int appointmentId) {

        ModelAndView result;

        Prescription prescription;

        Appointment a = appointmentService.findOneToEdit(appointmentId);

        prescription = prescriptionService.create(a);

        result = createEditModelAndView(prescription);

        result.addObject("prescription", prescription);
        result.addObject("isPatient", false);

        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid Prescription prescription, BindingResult binding) {

        ModelAndView result;

        if (binding.hasErrors()) {
            result = createEditModelAndView(prescription);
        } else {
            try {
                prescriptionService.save(prescription);
                result = new ModelAndView("redirect:../../appointment/specialist/edit.do?appointmentId="
                        + prescription.getAppointment().getId());
            } catch (Throwable oops) {
                result = createEditModelAndView(prescription, "prescription.commit.error");
            }
        }

        return result;

    }
    
    @RequestMapping(value = "/print", method = RequestMethod.GET)
    public void print(HttpServletResponse response, @RequestParam int prescriptionId) throws IOException {
        try {
            Prescription prescription;
            prescription = prescriptionService.findOneToEdit(prescriptionId);
            Document document = new Document();
            response.setHeader("Content-Disposition", "attachment;filename=" + prescription.getTitle());
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            Chunk chunkTitle = new Chunk(prescription.getTitle(), FontFactory.getFont(FontFactory.COURIER, 20,
                    Font.TIMES_ROMAN, Color.BLACK));

            Paragraph parrafo = new Paragraph();

            Chunk chunkDescription = new Chunk(prescription.getDescription(), FontFactory.getFont(FontFactory.COURIER,
                    14, Font.TIMES_ROMAN, Color.BLACK));
            Chunk chunkprice = new Chunk("Precio: " + prescription.getPrice().toString(), FontFactory.getFont(
                    FontFactory.COURIER, 14, Font.TIMES_ROMAN, Color.BLACK));
            Chunk chunkSpecialist = new Chunk("Especialista: "
                    + prescription.getAppointment().getSpecialist().getName() + " en "
                    + prescription.getAppointment().getSpecialist().getSpecialty().getName(), FontFactory.getFont(
                    FontFactory.COURIER, 14, Font.TIMES_ROMAN, Color.BLACK));
            chunkTitle.setBackground(Color.BLUE);
            document.add(chunkTitle);
            document.add(parrafo);
            document.add(chunkDescription);
            document.add(parrafo);
            document.add(chunkprice);
            document.add(parrafo);
            document.add(chunkSpecialist);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createEditModelAndView(Prescription prescription) {
        assert prescription != null;
        ModelAndView result;

        result = createEditModelAndView(prescription, null);

        return result;
    }

    protected ModelAndView createEditModelAndView(Prescription prescription, String message) {

        Assert.notNull(prescription);
        ModelAndView result;

        result = new ModelAndView("prescription/edit");

        result.addObject("prescription", prescription);
        result.addObject("isPatient", false);
        result.addObject("requestURI", "prescription/specialist/edit.do?prescriptionId=" + prescription.getId());
        result.addObject("message", message);

        return result;
    }

}