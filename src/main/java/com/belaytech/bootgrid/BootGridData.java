/*
 * Copyright (C) 2016 Belay Technologies
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.belaytech.bootgrid;

import java.util.List;
import lombok.Data;

/**
 *
 * @author Joshua
 * @param <T>
 */
@Data
public class BootGridData<T> {

    private int current;
    private int rowCount;
    private List<T> rows;
    private int total;

    public BootGridData(int current, int rowCount) {
        this.current = current;
        this.rowCount = rowCount;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
        this.total = rows.size();
    }
}
