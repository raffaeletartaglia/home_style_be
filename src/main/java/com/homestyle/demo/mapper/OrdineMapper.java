package com.homestyle.demo.mapper;
import org.mapstruct.Mapper;

import com.homestyle.demo.dto.request.OrdineRequestDTO;
import com.homestyle.demo.dto.response.OrdineResponseDTO;
import com.homestyle.demo.entity.Ordine;

import java.util.List;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
		componentModel = "spring",
		unmappedTargetPolicy = ReportingPolicy.ERROR // così non ti perdi campi per sbaglio
)
public interface OrdineMapper {

	// ===== DTO -> Entity (Request -> Ordine) =====
	// Qui ignori relazioni e campi che verranno gestiti nel service
	@BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "utente", ignore = true)
	@Mapping(target = "dataOrdine", ignore = true)
	@Mapping(target = "prezzoTotale", ignore = true)
	@Mapping(target = "indirizzoSpedizione", ignore = true)
	@Mapping(target = "dettagliOrdine", ignore = true)
	@Mapping(target = "pagamento", ignore = true)
	@Mapping(target = "spedizione", ignore = true)
	@Mapping(target = "movimentiMagazzino", ignore = true)
	Ordine toEntity(OrdineRequestDTO dto);

	// ===== Entity -> DTO (Ordine -> Response) =====
	// Qui prendo solo quello che ti serve nel DTO (esempio: utenteId)
	@BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
	//@Mapping(target = "utenteId", source = "utente.id")
	// se hai un mapper per Indirizzo:
	// @Mapping(target = "indirizzoSpedizione", source = "indirizzoSpedizione")
	// se nel DTO hai una lista di prodotti derivata dai dettagliOrdine:
	// @Mapping(target = "prodotti", ignore = true) // oppure definisci una logica custom
	OrdineResponseDTO toDTO(Ordine ordine);

	@BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
	List<OrdineResponseDTO> toDTOs(List<Ordine> ordines);
}
