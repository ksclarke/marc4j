/**
 * Copyright (C) 2002-2006 Bas Peters
 *
 * This file is part of MARC4J
 *
 * MARC4J is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * MARC4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with MARC4J; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.marc4j.samples;

import info.freelibrary.marc4j.converter.impl.AnselToUnicode;

import java.io.FileInputStream;
import java.io.InputStream;

import org.marc4j.MarcReader;
import org.marc4j.MarcStreamReader;
import org.marc4j.MarcWriter;
import org.marc4j.MarcXmlWriter;
import org.marc4j.marc.Record;

/**
 * Writes MARc XML to standard output
 *
 * @author Bas Peters
 */
public class Marc2MarcXmlExample {

    private Marc2MarcXmlExample() {
    }

    /**
     * The main class for the Marc2MarcXmlExample.
     *
     * @param args
     * @throws Exception
     */
    public static void main(final String args[]) throws Exception {
        final InputStream input = new FileInputStream("src/test/resources/summerland.mrc");
        final MarcReader reader = new MarcStreamReader(input);
        final MarcWriter writer = new MarcXmlWriter(System.out, true);
        final AnselToUnicode converter = new AnselToUnicode();

        writer.setConverter(converter);

        while (reader.hasNext()) {
            final Record record = reader.next();
            writer.write(record);
        }

        writer.close();
    }
}
