package com.yo1000.dbspock

import spock.lang.Specification

/**
 *
 * @author yo1000
 */
class TablesSpec extends Specification {
    def "Translate Closure to Table"() {
        setup:
        def data = {
            table1 {
                c1     | c2     | c3
                'r1c1' | 'r1c2' | 'r1c3'
                'r2c1' | 'r2c2' | 'r2c3'
            }

            table2 {
                cStr   | cInt   | cDate
                'str1' | 100    | '2016-09-26 18:46:01'
                'str2' | 200    | '2016-09-26 18:46:02'
            }
        }

        data.delegate = new Tables()
        data.call()

        def tables = data.tables as List<Table>

        tables.each {
            println "<<TABLE>>"
            println "table name: ${it.name}"
            println "<<COLUMNS>>"
            it.columns.columns.each {
                println "column name: ${it.name}"
            }
            println "<<ROWS>>"
            it.rows.rows.each {
                println "<<ROW>>"
                it.items.each {
                    println "row item value: ${it.value}"
                }
            }
        }

        expect:
        assert tables[0].name == 'table1'
        assert tables[0].columns.columns[0].name == 'c1'
        assert tables[0].columns.columns[1].name == 'c2'
        assert tables[0].columns.columns[2].name == 'c3'
        assert tables[0].rows.rows[0].items[0].value == 'r1c1'
        assert tables[0].rows.rows[0].items[1].value == 'r1c2'
        assert tables[0].rows.rows[0].items[2].value == 'r1c3'
        assert tables[0].rows.rows[1].items[0].value == 'r2c1'
        assert tables[0].rows.rows[1].items[1].value == 'r2c2'
        assert tables[0].rows.rows[1].items[2].value == 'r2c3'

        assert tables[1].name == 'table2'
        assert tables[1].columns.columns[0].name == 'cStr'
        assert tables[1].columns.columns[1].name == 'cInt'
        assert tables[1].columns.columns[2].name == 'cDate'
        assert tables[1].rows.rows[0].items[0].value == 'str1'
        assert tables[1].rows.rows[0].items[1].value == 100
        assert tables[1].rows.rows[0].items[2].value == '2016-09-26 18:46:01'
        assert tables[1].rows.rows[1].items[0].value == 'str2'
        assert tables[1].rows.rows[1].items[1].value == 200
        assert tables[1].rows.rows[1].items[2].value == '2016-09-26 18:46:02'
    }
}
