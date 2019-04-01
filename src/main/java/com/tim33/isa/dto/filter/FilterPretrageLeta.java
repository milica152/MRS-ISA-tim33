package com.tim33.isa.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterPretrageLeta {
    private String nazivAviokompanije;
    private double trajanjeLeta;
    private double maximalnaCena;  //filtriraju se svi koji imaju manju cenu od date

}
