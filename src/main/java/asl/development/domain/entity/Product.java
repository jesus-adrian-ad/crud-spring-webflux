package asl.development.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "products", schema = "public")
public class Product {

    @Id
    @Column("product_id")
    private Integer id;

    @Column("product_name")
    private String name;

    @Column("product_price")
    private String price;
}
