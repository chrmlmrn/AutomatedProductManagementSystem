package admin.product;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class DateChooserEditor extends AbstractCellEditor implements TableCellEditor {
    private JDateChooser dateChooser;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public DateChooserEditor() {
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
    }

    @Override
    public Object getCellEditorValue() {
        return sdf.format(dateChooser.getDate());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value instanceof String) {
            try {
                Date date = sdf.parse((String) value);
                dateChooser.setDate(date);
            } catch (ParseException e) {
                dateChooser.setDate(null);
            }
        } else if (value instanceof Date) {
            dateChooser.setDate((Date) value);
        } else {
            dateChooser.setDate(null);
        }
        return dateChooser;
    }
}
