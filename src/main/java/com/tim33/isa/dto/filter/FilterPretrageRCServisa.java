package com.tim33.isa.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterPretrageRCServisa {
    public String nazivServisa;
    public String adresaServisa;
    public LocalDate datumOd;
    public LocalDate datumDo;
}
