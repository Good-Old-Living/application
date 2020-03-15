package app.domain;

import app.domain.AppHierarchicalBaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="core_app_hierarchical_entity")
public class AppHierarchicalEntity extends AppHierarchicalBaseEntity implements app.entity.Hierarchical {

}
