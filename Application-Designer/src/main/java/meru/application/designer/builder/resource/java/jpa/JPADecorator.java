package meru.application.designer.builder.resource.java.jpa;

import meru.application.designer.builder.resource.SrcFileWriter;
import meru.application.designer.builder.resource.java.Decorator;
import meru.application.designer.domain.model.Attribute;
import meru.application.designer.domain.model.Entity;

public class JPADecorator implements Decorator {

	private SrcFileWriter mFileWriter;

	public void setFileWriter(SrcFileWriter fileWriter) {

		mFileWriter = fileWriter;
	}

	public void decorate(Entity entity) throws Exception {

		if (entity.containsOwningComplexAttribute()) {
			mFileWriter.writeln("import javax.persistence.CascadeType;");
		}

		if (entity.containsAttributes()) {
			mFileWriter.writeln("import javax.persistence.Column;");
		}

		Attribute attr = entity.getAttribute("id");

		if (attr != null) {
			mFileWriter.writeln("import javax.persistence.Id;");
			String seq = attr.getAttribute("sequence");
			if (seq != null) {
				if (seq.equalsIgnoreCase("Identity")) {
					mFileWriter.writeln("import javax.persistence.GeneratedValue;");
					mFileWriter.writeln("import javax.persistence.GenerationType;");
				}
			}
		}

		if (entity.isAbstract()) {
			mFileWriter.writeln("import javax.persistence.MappedSuperclass;");
		}
		else {
			mFileWriter.writeln("import javax.persistence.Entity;")
						.writeln("import javax.persistence.Table;");
		}

		if (entity.containsDateAttribute()) {
			mFileWriter.writeln("import javax.persistence.Temporal;")
						.writeln("import javax.persistence.TemporalType;");
		}

		if ((entity.containsComplexAttribute())
				|| (entity.containsComplexEnumAttribute())) {
			if (entity.containsMultiValuedAttribute()) {
				mFileWriter.writeln("import javax.persistence.OneToMany;");
			}

			if (entity.containsSingleValuedComplexAttribute()) {
				mFileWriter.writeln("import javax.persistence.OneToOne;");
				mFileWriter.writeln("import javax.persistence.JoinColumn;");
			}

		}

		if (entity.isAbstract()) {
			mFileWriter.writeln(new String[0]).writeln("@MappedSuperclass");
		}
		else
			mFileWriter.writeln(new String[0])
						.writeln("@Entity")
						.writeln(new String[] { "@Table(name=\"",
								entity.getDatabaseTableName(), "\")" });
	}

	public void decorate(Attribute attribute) throws Exception {

		mFileWriter.writeln(new String[0]).writeIndent();

		if (attribute.isTransient()) {
			mFileWriter.writeln("@javax.persistence.Transient");
			return;
		}

		if (attribute.isComplexType() || attribute.isComplexEnumType()) {
			if (attribute.isMultiValued()) {
				mFileWriter.write(new String[] { "@OneToMany" });
				if (!attribute.isOwningAttribute())
					return;
				String mappedBy = attribute.getAttribute("mappedBy");

				if (mappedBy == null) {
					throw new RuntimeException("mappedBy attribute is required for the attribute "
							+ attribute.getName());
				}

				mFileWriter.writeln(new String[] { "(mappedBy=\"", mappedBy,
						"\", cascade={CascadeType.ALL})" });
			}
			else {
				mFileWriter.write(new String[] { "@OneToOne" });

				if (attribute.isOwningAttribute()) {
					mFileWriter.write(new String[] { "(cascade={CascadeType.ALL})" });
				}

				mFileWriter.writeln(new String[0])
							.writeIndent()
							.write(new String[] { "@JoinColumn(name=\"",
									attribute.getDatabaseColumnName(), "\"" });

				if (attribute.isRequired()) {
					mFileWriter.write(", nullable=false");
				}

				mFileWriter.writeln(")");
			}
		}
		else {
			if (attribute.isId()) {
				mFileWriter.write(new String[] { "@Id" }).writeln();
				String value = attribute.getAttribute("sequence");
				if (value != null) {
					if (value.equalsIgnoreCase("Identity")) {
						mFileWriter.writeIndent()
									.writeln("@GeneratedValue(strategy=GenerationType.IDENTITY)");
					}
				}

				mFileWriter.writeIndent();
			}

			mFileWriter.write(new String[] { "@Column(name=\"",
					attribute.getDatabaseColumnName(), "\"" });

			if (attribute.isRequired()) {

				if (!attribute.isBooleanType()) {
					mFileWriter.write(", nullable=false");
				}
			}

			mFileWriter.writeln(")");

			if (attribute.isDateType()) {
				mFileWriter.writeIndent()
							.writeln("@Temporal(TemporalType.TIMESTAMP)");
			}
		}
	}
}