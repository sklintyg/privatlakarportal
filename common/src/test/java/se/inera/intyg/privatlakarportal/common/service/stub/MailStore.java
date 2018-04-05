/*
 * Copyright (C) 2017 Inera AB (http://www.inera.se)
 *
 * This file is part of sklintyg (https://github.com/sklintyg).
 *
 * sklintyg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * sklintyg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.inera.intyg.privatlakarportal.common.service.stub;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;

/**
 * @author andreaskaltenbach
 */
@Component
public class MailStore {

    private static final long MAX_TIMEOUT = 5000;
    public static final long POLL_INTERVAL = 10L;
    public static final int MAX_POLLS = 100;
    private List<OutgoingMail> mails = new CopyOnWriteArrayList<>();
    private boolean doWait;

    public List<OutgoingMail> getMails() {
        return mails;
    }

    public void waitForMails(int count) {
        int loops = MAX_POLLS;
        while (mails.size() < count) {
            try {
                Thread.sleep(POLL_INTERVAL);
            } catch (InterruptedException e) {
                if (--loops == 0) {
                    break;
                }
            }
        }
    }

    //CHECKSTYLE:OFF EmptyBlock
    public void waitToContinue() {
        synchronized (this) {
            if (doWait) {
                try {
                    this.wait(MAX_TIMEOUT);
                } catch (InterruptedException e) {
                }
            }
        }
    }
    //CHECKSTYLE:ON EmptyBlock

    public void setWait(boolean doWait) {
        synchronized (this) {
            this.doWait = doWait;
            this.notifyAll();
        }
    }
}
