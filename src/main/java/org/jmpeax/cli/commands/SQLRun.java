package org.jmpeax.cli.commands;

import org.jmpeax.cli.api.events.ConnectionChange;
import org.jmpeax.cli.api.providers.DataSource;
import org.jmpeax.cli.ext.UserInput;
import org.springframework.context.event.EventListener;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.AbsoluteWidthSizeConstraints;
import org.springframework.shell.table.Aligner;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.CellMatchers;
import org.springframework.shell.table.SizeConstraints;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;

import java.util.Map;

@ShellComponent
public class SQLRun {

    /**
     * Current connection registry
     */
    private Map<String, DataSource> connectionRegistry;

    /**
     * Current connection Name.
     */
    private String currentConnection;
    /**
     * Default CTR.
     * @param connRegistry Connection Registry
     */
    public SQLRun(final Map<String, DataSource> connRegistry) {
        this.connectionRegistry = connRegistry;
        currentConnection = ConnectCmd.DEFAULT_CONN;
    }

    /**
     * runs the SQL agains the DS.
     * @param sqlStatement Statement to run.
     */
    @ShellMethod("Runs the given SQL statement.")
    public String $(@UserInput() final String sqlStatement){
        if (connectionRegistry.containsKey(currentConnection)) {
            final DataSource ds = connectionRegistry.get(currentConnection);
            final TableModel tbl = ds.exec(sqlStatement);
            if(tbl!=null) {
                TableBuilder tableBuilder = new TableBuilder(tbl);
                tableBuilder.addFullBorder(BorderStyle.oldschool);
              return tableBuilder.build().render(120);
            }
            return "";
       } else {
            throw new IllegalStateException("There is not configure connection");
        }
    }

    /**
     * Listends and reacts to a connection Change.
     * @param connectionName connection Name.
     */
    @EventListener
    public void handle(final ConnectionChange connectionName) {
        this.currentConnection = connectionName.getConnectionName();
    }


}
