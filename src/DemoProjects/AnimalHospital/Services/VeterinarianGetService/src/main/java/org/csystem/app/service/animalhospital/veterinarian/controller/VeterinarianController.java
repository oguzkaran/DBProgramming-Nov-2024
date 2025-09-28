package org.csystem.app.service.animalhospital.veterinarian.controller;

import com.karandev.util.data.service.DataServiceException;
import org.csystem.app.service.animalhospital.veterinarian.dto.VeterinarianDTO;
import org.csystem.app.service.animalhospital.veterinarian.dto.VeterinarianError;
import org.csystem.app.service.animalhospital.veterinarian.dto.VeterinarianStatus;
import org.csystem.app.service.animalhospital.veterinarian.dto.VeterinarianStatusDTO;
import org.csystem.app.service.animalhospital.veterinarian.mapper.IVeterinarianMapper;
import org.csystem.app.service.animalhospital.veterinarian.service.VeterinarianService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.karandev.util.exception.ExceptionUtil.subscribe;

@RestController
@RequestMapping("api/read/vets")
public class VeterinarianController {
    private final VeterinarianService m_veterinarianService;

    private final IVeterinarianMapper m_veterinarianMapper;

    public VeterinarianController(VeterinarianService veterinarianService, IVeterinarianMapper veterinarianMapper)
    {
        m_veterinarianService = veterinarianService;
        m_veterinarianMapper = veterinarianMapper;
    }

    @GetMapping("count")
    public ResponseEntity<Object> count()
    {
        ResponseEntity<Object> result;

        try {
            result = ResponseEntity.ok(m_veterinarianService.countVeterinarians());
        }
        catch (DataServiceException ignore) {
            result = ResponseEntity.internalServerError().body(new VeterinarianError("Internal problem occurs!...Try again later", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }

        return result;

    }

    @GetMapping("all")
    public ResponseEntity<Object> findAll()
    {
        ResponseEntity<Object> result;

        try {
            result = ResponseEntity.ok(m_veterinarianService.findAllVeterinarians());
        }
        catch (DataServiceException ignore) {
            result = ResponseEntity.internalServerError().body(new VeterinarianError("Internal problem occurs!...Try again later", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }

        return result;
    }

    /*
        Aşağıdaki 4 metot, farklı yaklaşımları göstermektedir. Bu yaklaşımların hangisinin olacağı servise bağlıdır.
        Burada örnek amaçlı 3 tanesi ayrı ayrı yazılmıştır. Yoksa servis özelinde bir tanesi tercih edilebilir
    */

    @GetMapping("vet/diploma/404")
    public ResponseEntity<VeterinarianDTO> findByDiplomaNo404(@RequestParam("no") long diplomaNo)
    {
        return ResponseEntity.of(m_veterinarianService.findVeterinarianByDiplomaNo(diplomaNo));
    }

    @GetMapping("vet/diploma/200/difftypes")
    public Object findByDiplomaNoObject(@RequestParam("no") long diplomaNo)
    {
        var result = m_veterinarianService.findVeterinarianByDiplomaNo(diplomaNo);

        return result.isPresent() ? result.get() : new VeterinarianStatus("Not found", diplomaNo);
    }

    @GetMapping("vet/diploma/200/status")
    public VeterinarianStatusDTO findByDiplomaNoStatus(@RequestParam("no") long diplomaNo)
    {
        var result = m_veterinarianService.findVeterinarianByDiplomaNo(diplomaNo);

        return result.isPresent() ? m_veterinarianMapper.toVeterinarianStatus(result.get()) : new VeterinarianStatusDTO(false);
    }

    @GetMapping("vet/diploma")
    public ResponseEntity<Object> findByDiplomaNo(@RequestParam("no") String diplomaNoStr)
    {
        ResponseEntity<Object> result;

        try {
            var info = m_veterinarianService.findVeterinarianByDiplomaNo(Long.parseLong(diplomaNoStr));

            result = ResponseEntity.ok(info.isPresent() ? m_veterinarianMapper.toVeterinarianStatus(info.get()) : new VeterinarianStatusDTO(false));
        }
        catch (NumberFormatException ignore) {
            result = ResponseEntity.badRequest().body(new VeterinarianError("Invalid diploma number", HttpStatus.BAD_REQUEST.value()));
        }
        catch (DataServiceException ignore) {
            result = ResponseEntity.internalServerError().body(new VeterinarianError("Internal problem occurs!...Try again later", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }

        return result;
    }

    @GetMapping("lastname")
    public ResponseEntity<Object> findByLastName(@RequestParam("n") String lastName)
    {
        ResponseEntity<Object> result;

        try {
            result = ResponseEntity.ok(m_veterinarianService.findVeterinariansByLastName(lastName));
        }
        catch (DataServiceException ignore) {
            result = ResponseEntity.internalServerError().body(new VeterinarianError("Internal problem occurs!...Try again later", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }

        return result;
    }

    @GetMapping("monthyear")
    public ResponseEntity<Object> findByMonthAndYear(@RequestParam("m") int month, @RequestParam("y")int year)
    {
        return subscribe(() -> ResponseEntity.ok(m_veterinarianService.findVeterinariansByMonthAndYear(month, year)),
                ignore -> ResponseEntity.internalServerError().body(new VeterinarianError("Internal problem occurs!...Try again later", HttpStatus.INTERNAL_SERVER_ERROR.value())));
    }

    @GetMapping("between/year")
    public ResponseEntity<Object> findByYearBetween(@RequestParam("begin") int begin, @RequestParam("end")int end)
    {
        return subscribe(() -> ResponseEntity.ok(m_veterinarianService.findVeterinariansByYearBetween(begin, end)),
                ignore -> ResponseEntity.internalServerError().body(new VeterinarianError("Internal problem occurs!...Try again later", HttpStatus.INTERNAL_SERVER_ERROR.value())));
    }

    @GetMapping("name/full")
    public ResponseEntity<Object> findAllWithFullName()
    {
        return subscribe(() -> ResponseEntity.ok(m_veterinarianService.findAllVeterinariansWithFullName()),
                ignore -> ResponseEntity.internalServerError().body(new VeterinarianError("Internal problem occurs!...Try again later", HttpStatus.INTERNAL_SERVER_ERROR.value())));
    }
}
