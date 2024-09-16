package corsi.prog2.ministerio.controlador;

import corsi.prog2.ministerio.modelo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ABMSucursales {

    @Autowired
    private RepositorioDeSucursales rs;

    @Autowired
    private RepositorioDeUsuarios ru;  // Asumiendo que necesitas obtener el usuario admin
    @Autowired
    private RepositorioDeEntidades re;

    @GetMapping("/sucursales")
    public String listarSucursales(Model model) {
        model.addAttribute("sucursales", rs.findAll());
        model.addAttribute("entidades", re.findAll());
        return "sucursales";
    }



    @PostMapping("/nueva-sucursal")
    public String nuevaSucursal(final RedirectAttributes redirectAttributes,
                                @RequestParam(name = "codigo") String codigo,
                                @RequestParam(name = "domicilio") String domicilio,
                                @RequestParam(name = "cantEmpleados") String cantEmpleados,
                                @RequestParam(name = "admin") String admin) {

        Sucursal sucursal = new Sucursal();
        sucursal.setCodigo(codigo);
        sucursal.setDomicilio(domicilio);
        sucursal.setCantidadEmpleados(cantEmpleados);

        rs.saveAndFlush(sucursal);

        Usuario usuario = ru.findById(admin).orElse(null);  // Obtener el usuario admin
        redirectAttributes.addFlashAttribute("usuario", usuario);

        return "redirect:/login#sucursales";
    }

    @PostMapping("/editar-borrar-sucursal")
    public String editarBorrarSucursal(final RedirectAttributes redirectAttributes,
                                       @RequestParam(name = "id") Long id,
                                       @RequestParam(name = "codigo") String codigo,
                                       @RequestParam(name = "cantEmpleados") String cantEmpleados,
                                       @RequestParam(name = "domicilio") String domicilio,
                                       @RequestParam(name = "admin") String admin,
                                       @RequestParam(name = "accion") String accion) {
        if (accion.equals("Borrar")) {
            rs.deleteById(id);
        } else if (accion.equals("Editar")) {
            Sucursal editado = rs.findById(id).orElse(null);
            if (editado != null) {
                editado.setCodigo(codigo);
                editado.setCantidadEmpleados(cantEmpleados);
                editado.setDomicilio(domicilio);
                rs.saveAndFlush(editado);
            }
        }
        Usuario usuario = ru.findById(admin).orElse(null);  // Obtener el usuario admin
        redirectAttributes.addFlashAttribute("usuario", usuario);

        return "redirect:/login#sucursales";
    }

}
